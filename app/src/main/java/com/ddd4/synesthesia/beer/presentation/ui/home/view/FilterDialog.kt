package com.ddd4.synesthesia.beer.presentation.ui.home.view

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
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
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterDialog
    : BaseBottomSheetDialogFragment<LayoutFilterBinding>(R.layout.layout_filter) {

    private val viewModel: FilterViewModel by viewModels()

    private val countryListAdapter by lazy {
        FilterCountryAdapter(viewModel.countrySelectedList)
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

        initBind()
        initObserving()
    }


    private fun ChipGroup.setChips(
        items: List<String>,
        selectedItemList: MutableLiveDataList<String>,
        shapeModel: ShapeAppearanceModel
    ) {
        for (item in items) {
            val chip: Chip = layoutInflater.inflate(
                R.layout.layout_filter_chip,
                binding.aromaChipGroup,
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


    override fun initObserving() {
        viewModel.countryList.observe(viewLifecycleOwner, Observer {
            countryListAdapter.items = it
        })

    }

    override fun initBind() {
        binding.apply {
            vm = viewModel
            adapter = countryListAdapter

            styleChipGroup.setChips(
                viewModel.styleList,
                viewModel.styleSelectedList,
                ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 8f).build()
            )

            aromaChipGroup.setChips(
                viewModel.aromaList,
                viewModel.aromaSelectedList,
                ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 50f).build()
            )

            abvSeekbar.apply {
                val range = viewModel.abvSelectedRange.value ?: 0 to 10
                setRange(0f, 10f, 1f)
                setProgress(range.first.toFloat(), range.second.toFloat())

                setOnRangeChangedListener(object : OnRangeChangedListener {
                    override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
                        //
                    }

                    override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
                        viewModel.abvSelectedRange.postValue(
                            (view?.leftSeekBar?.progress?.toInt()
                                ?: 0) to (view?.rightSeekBar?.progress?.toInt() ?: 10)
                        )
                    }

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

            btnDone.setOnClickListener {
                viewModel.executeFiltering()
                dismiss()
            }
        }

    }
}