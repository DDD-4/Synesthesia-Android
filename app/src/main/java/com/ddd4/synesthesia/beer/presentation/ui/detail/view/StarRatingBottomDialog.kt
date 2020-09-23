package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.LayoutBottomStarRatingBinding
import com.ddd4.synesthesia.beer.ext.showToast
import com.ddd4.synesthesia.beer.ext.start
import com.ddd4.synesthesia.beer.presentation.base.BaseBottomSheetDialogFragment
import com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel.StarRatingViewModel
import com.ddd4.synesthesia.beer.util.SimpleCallback
import com.hyden.ext.start
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarRatingBottomDialog : BaseBottomSheetDialogFragment<LayoutBottomStarRatingBinding>(R.layout.layout_bottom_star_rating) {

    private val starRatingViewModel by viewModels<StarRatingViewModel>()
    private val id by lazy { arguments?.get(getString(R.string.key_id)) as? Int }
    private var dismiss: (() -> Unit)? = null
    private val minRating = 0.5f

    private val reviewCallback = object : SimpleCallback {
        override fun call(text: String) {
            starRatingViewModel.review.value = text
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SimpleCallback.callback = reviewCallback
        binding.apply {
            vm = starRatingViewModel
            btnSendReview.setOnClickListener {
                id?.let { id ->
                    starRatingViewModel.postReview(id)
                } ?: kotlin.run { context?.showToast(getString(R.string.review_fail_message)) }
            }
            edtReview.setOnClickListener {
                val bundle = bundleOf(
                    getString(R.string.key_review) to starRatingViewModel.review.value
                )
                start<WriteReviewActivity>(false, bundle)
            }
        }
        initObserving()
    }

    override fun onDestroy() {
        super.onDestroy()
        SimpleCallback.callback = null
    }

    fun showDialog(fm : FragmentManager, dismiss : (() -> Unit)?) {
        show(fm,"")
        this@StarRatingBottomDialog.dismiss = dismiss
    }
    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }

    override fun initBind() {

    }

    override fun initObserving() {
        starRatingViewModel.register.observe(viewLifecycleOwner, Observer {
            dismiss()
            dismiss?.invoke()
        })
        starRatingViewModel.rating.observe(viewLifecycleOwner, Observer {
            if(it < minRating) {
                starRatingViewModel.rating.value = minRating
            }
        })
    }
}