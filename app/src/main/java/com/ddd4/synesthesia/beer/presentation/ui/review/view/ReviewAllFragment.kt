package com.ddd4.synesthesia.beer.presentation.ui.review.view

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.FragmentReviewBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.BaseItemsApdater
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewAllFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    private val args: ReviewAllFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            reviewAdatper = BaseItemsApdater(R.layout.layout_review,BR.review)
            includeToolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        args.review?.toList()?.let {
            binding.reviewAdatper?.updateItems(it)
        }
    }
}