package com.ddd4.synesthesia.beer.presentation.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.LayoutBottomSortBinding
import com.ddd4.synesthesia.beer.ext.updateTypeface
import com.ddd4.synesthesia.beer.presentation.base.BaseBottomSheetDialogFragment
import com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel.HomeSortViewModel
import com.ddd4.synesthesia.beer.util.sort.SortType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeSortDialog
    : BaseBottomSheetDialogFragment<LayoutBottomSortBinding>(R.layout.layout_bottom_sort) {

    private val viewModel: HomeSortViewModel by viewModels()

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initBind()
    }

    override fun initBind() {
        binding.vm = viewModel

        viewModel.sortType.observe(viewLifecycleOwner, Observer {
            updateTypeface(it)
        })

        binding.sortClose.setOnClickListener {
            this.dismiss()
        }
    }

    private fun updateTypeface(sortType: SortType) {

        val typeBold = ResourcesCompat.getFont(
            requireContext(),
            R.font.notosans_kr_bold
        ) to resources.getColor(R.color.butterscotch, null)

        val typeRegular = ResourcesCompat.getFont(
            requireContext(),
            R.font.notosans_kr_regular
        ) to resources.getColor(R.color.white, null)

        binding.apply {
            sortDefault.updateTypeface(typeRegular)
            sortRating.updateTypeface(typeRegular)
            sortReview.updateTypeface(typeRegular)

            when (sortType) {
                SortType.Default -> sortDefault.updateTypeface(typeBold)
                SortType.Rating -> sortRating.updateTypeface(typeBold)
                SortType.Review -> sortReview.updateTypeface(typeBold)
            }
        }
    }

    override fun initObserving() {

    }
}