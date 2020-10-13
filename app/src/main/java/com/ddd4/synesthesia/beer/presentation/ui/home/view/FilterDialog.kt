package com.ddd4.synesthesia.beer.presentation.ui.home.view

import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.AppConfig
import com.ddd4.synesthesia.beer.databinding.LayoutFilterBinding
import com.ddd4.synesthesia.beer.ext.dp
import com.ddd4.synesthesia.beer.presentation.base.BaseBottomSheetDialogFragment
import com.ddd4.synesthesia.beer.presentation.ui.home.adapter.FilterCountryAdapter
import com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel.FilterViewModel
import com.ddd4.synesthesia.beer.util.CustomAlertDialog
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
import kotlinx.coroutines.launch

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
                ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 4f.dp.toFloat()).build()
            )

            aromaChipGroup.setChips(
                R.layout.layout_filter_aroma_chip,
                viewModel.aromaList,
                viewModel.aromaSelectedList,
                ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 50f.dp.toFloat()).build()
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
                CustomAlertDialog(
                    title = getString(R.string.page_out),
                    message = getString(R.string.reset_filter),
                    posivie = getString(R.string.yes),
                    negative = getString(R.string.no),
                    result = DialogInterface.OnClickListener { _, _ ->

                        lifecycleScope.launch {
                            viewModel.resetAllAsync().await()
                            btnDone.performClick()
                        }
                    }
                ).show(parentFragmentManager, null)
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