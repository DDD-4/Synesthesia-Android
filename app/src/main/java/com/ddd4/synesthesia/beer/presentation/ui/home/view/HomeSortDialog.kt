package com.ddd4.synesthesia.beer.presentation.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.LayoutBottomSortBinding
import com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel.HomeSortViewModel
import com.ddd4.synesthesia.beer.util.sort.SortType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeSortDialog
    : BottomSheetDialogFragment() {

    private lateinit var binding: LayoutBottomSortBinding

    private val viewModel: HomeSortViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(this.context),
            R.layout.layout_bottom_sort,
            null,
            false
        )
        binding.vm = viewModel
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.sortType.observe(viewLifecycleOwner, Observer {
            updateTypeface(it)
        })

        binding.sortClose.setOnClickListener {
            this.dismiss()
        }
    }

    private fun updateTypeface(sortType: SortType) {
        binding.apply {
            when (sortType) {
                SortType.Default -> {
                    sortDefault.typeface =
                        ResourcesCompat.getFont(requireContext(), R.font.notosans_kr_bold)
                    sortComment.typeface =
                        ResourcesCompat.getFont(requireContext(), R.font.notosans_kr_regular)
                    sortReview.typeface =
                        ResourcesCompat.getFont(requireContext(), R.font.notosans_kr_regular)
                }

                SortType.Comment -> {
                    sortComment.typeface =
                        ResourcesCompat.getFont(requireContext(), R.font.notosans_kr_bold)
                    sortDefault.typeface =
                        ResourcesCompat.getFont(requireContext(), R.font.notosans_kr_regular)
                    sortReview.typeface =
                        ResourcesCompat.getFont(requireContext(), R.font.notosans_kr_regular)
                }

                SortType.Review -> {
                    sortReview.typeface =
                        ResourcesCompat.getFont(requireContext(), R.font.notosans_kr_bold)
                    sortDefault.typeface =
                        ResourcesCompat.getFont(requireContext(), R.font.notosans_kr_regular)
                    sortComment.typeface =
                        ResourcesCompat.getFont(requireContext(), R.font.notosans_kr_regular)
                }
            }
        }
    }

}