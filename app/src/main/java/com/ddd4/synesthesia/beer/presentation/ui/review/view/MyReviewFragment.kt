package com.ddd4.synesthesia.beer.presentation.ui.review.view

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ddd4.synesthesia.beer.HomeNavigationDirections
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.Review
import com.ddd4.synesthesia.beer.databinding.FragmentMyReviewBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.BaseItemsApdater
import com.ddd4.synesthesia.beer.presentation.ui.review.viewmodel.MyReviewViewModel
import com.ddd4.synesthesia.beer.util.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyReviewFragment : BaseFragment<FragmentMyReviewBinding>(R.layout.fragment_my_review) {

    private val viewModel by viewModels<MyReviewViewModel>()
    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T?) {
                (item as? Review)?.beer?.id?.let {
                    findNavController().navigate(HomeNavigationDirections.actionToDetail(it))
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            reviewAdatper = BaseItemsApdater(R.layout.layout_my_review,BR.review,itemClickListener)
            srvReview.setOnRefreshListener {
                viewModel.review()
            }
        }
        initObserving()
    }

    override fun initObserving() {
        super.initObserving()
        viewModel.myReviews.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                binding.reviewAdatper?.updateItems(list)
                binding.srvReview.isRefreshing = false
            }
        })
    }
}