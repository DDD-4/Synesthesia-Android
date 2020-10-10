package com.ddd4.synesthesia.beer.presentation.ui.home.view

import android.app.AlertDialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.AppConfig
import com.ddd4.synesthesia.beer.databinding.LayoutFilterBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseBottomSheetDialogFragment
import com.ddd4.synesthesia.beer.presentation.ui.home.adapter.FilterCountryAdapter
import com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel.FilterViewModel
import com.ddd4.synesthesia.beer.util.MutableLiveDataList
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.gson.Gson
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterDialog
    : BaseBottomSheetDialogFragment<LayoutFilterBinding>(R.layout.layout_filter) {

    private val viewModel: FilterViewModel by viewModels()

    private val countryListAdapter by lazy {
        FilterCountryAdapter(viewModel.countrySelectedList).apply {
            setHasStableIds(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setOnShowListener {
            val bottomSheetInternal =
                (it as BottomSheetDialog).findViewById<View>(R.id.design_bottom_sheet)

            bottomSheetInternal?.layoutParams =
                bottomSheetInternal?.layoutParams?.apply {
                    height = getBottomWindowHeight()
                }

            it.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        getAppConfig()
        initBind()
    }


    private fun ChipGroup.setChips(
        layout: Int,
        items: List<String>,
        selectedItemList: MutableLiveDataList<String>,
        shapeModel: ShapeAppearanceModel
    ) {
        for (item in items) {
            val chip: Chip = layoutInflater.inflate(
                layout,
                this,
                false
            ) as Chip

            chip.apply {
                text = item
                shapeAppearanceModel = shapeModel
                if (selectedItemList.isNotEmpty() && selectedItemList.contains(item)) {
                    this.isChecked = true
                }

                setOnCheckedChangeListener { v: CompoundButton, isChecked: Boolean ->
                    val value = v.text.toString()
                    if (isChecked) selectedItemList.add(value) else selectedItemList.remove(value)
                }
            }
            addView(chip)
        }
    }

    private fun getBottomWindowHeight(): Int =
        Resources.getSystem().displayMetrics.heightPixels.times(0.9).toInt()

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }

    override fun initBind() {
        binding.apply {
            vm = viewModel
            adapter = countryListAdapter

            countryListAdapter.items = viewModel.countryList

            styleChipGroup.setChips(
                R.layout.layout_filter_style_chip,
                viewModel.styleList,
                viewModel.styleSelectedList,
                ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 8f).build()
            )

            aromaChipGroup.setChips(
                R.layout.layout_filter_aroma_chip,
                viewModel.aromaList,
                viewModel.aromaSelectedList,
                ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 50f).build()
            )

            abvSeekbar.apply {
                val range = viewModel.abvSelectedRange.value ?: viewModel.minAbv to viewModel.maxAbv
                setRange(viewModel.minAbv.toFloat(), viewModel.maxAbv.toFloat(), 1f)
                setProgress(range.first.toFloat(), range.second.toFloat())

                setOnRangeChangedListener(object : OnRangeChangedListener {
                    override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}
                    override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}

                    override fun onRangeChanged(
                        view: RangeSeekBar?,
                        leftValue: Float,
                        rightValue: Float,
                        isFromUser: Boolean
                    ) {
                        viewModel.abvSelectedRange.postValue(leftValue.toInt() to rightValue.toInt())
                    }

                })

            }

            btnClose.setOnClickListener {
                dismiss()
            }

            btnReset.setOnClickListener {
                // TODO 디자인 버튼 위치 등 확인 후 커스텀 진행 예정
                AlertDialog.Builder(requireContext())
                    .setTitle("초기화 하시겠습니까?")
                    .setMessage("적용한 필터 내용이 사라집니다!")
                    .setPositiveButton(
                        "네"
                    ) { _, _ ->

                        abvSeekbar.setProgress(
                            viewModel.minAbv.toFloat(),
                            viewModel.maxAbv.toFloat()
                        )
                        with(viewModel) {
                            styleSelectedList.clear()
                            aromaSelectedList.clear()
                            abvSelectedRange.postValue(null)
                            countrySelectedList.postValue(mutableListOf())
                        }
                        styleChipGroup.clearCheck()
                        aromaChipGroup.clearCheck()
                        countryListAdapter.notifyDataSetChanged()
                    }.setNegativeButton("아니오") { _, _ ->
                        dismiss()
                    }.create().show()
            }

            btnDone.setOnClickListener {
                viewModel.executeFiltering()
                dismiss()
            }
        }
    }

    private fun getAppConfig() {
        val value = preference.getPreferenceString("appConfig") ?: return
        val result = Gson().fromJson(value, AppConfig::class.java)
        with(viewModel) {
            styleList.addAll(result.styleList)
            aromaList.addAll(result.aromaList)
            countryList.addAll(result.countryList)
            minAbv = result.minAbv
            maxAbv = result.maxAbv
        }
    }

    override fun initObserving() {
        //
    }
}