package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.model.Review
import com.ddd4.synesthesia.beer.databinding.FragmentDetailBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.BaseItemsApdater
import com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel.DetailViewModel
import com.ddd4.synesthesia.beer.util.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_global_toolbar.view.*

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {
    private val detailViewModel by viewModels<DetailViewModel>()

    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T) {
                when(item) {
                    // 리뷰
                    is Review -> {

                    }
                    // 향
                    is String -> {

                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            vm = detailViewModel
            aromaAdapter = BaseItemsApdater(R.layout.layout_aroma, BR.scent ,itemClickListener)
            reviewAdapter = BaseItemsApdater(R.layout.layout_review, BR.review ,itemClickListener)
            inclideToolbar.toolbar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
            rbBeerRate.setOnTouchListener { v, event ->
                when(event.action) {
                    MotionEvent.ACTION_UP -> {
                        StarRatingBottomDialog().run {
                            show(this@DetailFragment.parentFragmentManager,tag)
                        }
                    }
                }
                true
            }
        }
        val data = arguments?.get(getString(R.string.key_data)) as? Beer
        data?.id?.let {
            detailViewModel.fetchBeer(it)
        }
        initObserving()
    }

    override fun initObserving() {
        super.initObserving()
        detailViewModel.beer.observe(viewLifecycleOwner, Observer {
            it.reviews?.let { reviews ->
                binding.reviewAdapter?.updateItems(reviews)
            }
            it.aromas.let { aromas ->
                binding.aromaAdapter?.updateItems(aromas)
            }
        })
    }
}