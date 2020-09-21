package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.LayoutBottomStarRatingBinding
import com.ddd4.synesthesia.beer.ext.showToast
import com.ddd4.synesthesia.beer.presentation.base.BaseBottomSheetDialogFragment
import com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel.StarRatingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarRatingBottomDialog : BaseBottomSheetDialogFragment<LayoutBottomStarRatingBinding>(R.layout.layout_bottom_star_rating) {

    private val starRatingViewModel by viewModels<StarRatingViewModel>()
    private val id by lazy { arguments?.get(getString(R.string.key_id)) as? Int }
    private var dismiss: (() -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            vm = starRatingViewModel
            btnSendReview.setOnClickListener {
                id?.let { id ->
                    starRatingViewModel.postReview(id)
                } ?: kotlin.run { context?.showToast(getString(R.string.review_fail_message)) }
            }
        }
        initObserving()
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
    }
}