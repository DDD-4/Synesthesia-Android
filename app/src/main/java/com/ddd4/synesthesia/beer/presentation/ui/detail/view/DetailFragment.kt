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
    private val beer by lazy { arguments?.get(getString(R.string.key_data)) as? Beer }

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
        val data = arguments?.get(getString(R.string.key_data)) as? Beer
        binding.apply {
            vm = detailViewModel
            aromaAdapter = BaseItemsApdater(R.layout.layout_aroma, BR.scent ,itemClickListener)
            reviewAdapter = BaseItemsApdater(R.layout.layout_review, BR.review ,itemClickListener)
            aromaRelatedAdapter = BaseItemsApdater(R.layout.layout_beer_card, BR.related, itemClickListener)
            styleRelatedAdapter = BaseItemsApdater(R.layout.layout_beer_card, BR.related, itemClickListener)
            randomRelatedAdapter = BaseItemsApdater(R.layout.layout_beer_card, BR.related, itemClickListener)

            inclideToolbar.toolbar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
            rbBeerRate.setOnTouchListener { v, event ->
                when(event.action) {
                    MotionEvent.ACTION_UP -> {
                        StarRatingBottomDialog().run {
                            beer?.let {
                                val bundle = Bundle()
                                bundle.putInt("id",it.id)
                                this@run.arguments = bundle
                            }
                            showDialog(this@DetailFragment.parentFragmentManager) {
                                data?.id?.let { detailViewModel.fetchBeer(it) }
                            }
                        }
                    }
                }
                true
            }
        }
        data?.id?.let { detailViewModel.fetchBeer(it) }
        initObserving()
    }

    override fun initObserving() {
        super.initObserving()
        detailViewModel.result.observe(viewLifecycleOwner, Observer { result ->
            result.beer?.reviews?.let { binding.reviewAdapter?.updateItems(it) }
            result.beer?.aromas?.let { binding.aromaAdapter?.updateItems(it) }
            result.relatedBeers?.aromaRelated?.let { binding.aromaRelatedAdapter?.updateItems(it) }
            result.relatedBeers?.styleRelated?.let { binding.styleRelatedAdapter?.updateItems(it) }
            result.relatedBeers?.randomlyRelated?.let { binding.randomRelatedAdapter?.updateItems(it) }
        })
    }
}