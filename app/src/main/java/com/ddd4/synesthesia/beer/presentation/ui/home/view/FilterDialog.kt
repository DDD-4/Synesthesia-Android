package com.ddd4.synesthesia.beer.presentation.ui.home.view

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.LayoutFilterBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseBottomSheetDialogFragment
import com.ddd4.synesthesia.beer.presentation.base.BaseItemsApdater
import com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel.FilterViewModel
import com.ddd4.synesthesia.beer.util.ItemClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterDialog
    : BaseBottomSheetDialogFragment<LayoutFilterBinding>(R.layout.layout_filter) {

    private val viewModel: FilterViewModel by viewModels()

    private val styleSelectedList = arrayListOf<String>()
    private val aromaSelectedList = arrayListOf<String>()

    private val countryListAdapter by lazy {
        BaseItemsApdater(
            R.layout.item_filter_country,
            BR.country,
            itemClickListener
        )
    }

    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T) {
                //
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.setOnShowListener {
            val bottomSheetInternal =
                (it as BottomSheetDialog).findViewById<View>(R.id.design_bottom_sheet)

            bottomSheetInternal?.layoutParams =
                bottomSheetInternal?.layoutParams?.apply {
                    height = getBottomWindowHeight()
                }

            it.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.apply {
            vm = viewModel
            adapter = countryListAdapter

            styleChipGroup.setChips(
                viewModel.styleList,
                styleSelectedList,
                tvStyleCount,
                ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 8f).build()
            )

            aromaChipGroup.setChips(
                viewModel.aromaList,
                aromaSelectedList,
                tvAromaCount,
                ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 50f).build()
            )
        }

        super.onViewCreated(view, savedInstanceState)

        initBind()
    }


    private fun ChipGroup.setChips(
        items: List<String>,
        selectedItemList: MutableList<String>,
        countView: TextView,
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
                setOnCheckedChangeListener { v: CompoundButton, isChecked: Boolean ->
                    val value = v.text.toString()
                    if (isChecked) selectedItemList.add(value) else selectedItemList.remove(value)
                    if (selectedItemList.isNotEmpty()) {
                        val suffix = "${selectedItemList.count().minus(1)}개"
                        countView.text =
                            if (selectedItemList.count() > 1) "${selectedItemList[0]} 외 $suffix" else selectedItemList[0]
                    } else {
                        countView.text = ""
                    }
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
        viewModel.countryList.observe(viewLifecycleOwner, Observer {
            countryListAdapter.updateItems(it)
        })
    }
}