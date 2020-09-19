package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.LayoutBottomStarRatingBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseBottomSheetDialogFragment
import com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel.StarRatingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarRatingBottomDialog : BaseBottomSheetDialogFragment<LayoutBottomStarRatingBinding>(R.layout.layout_bottom_star_rating) {
    private val starRatingViewModel by viewModels<StarRatingViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            vm = starRatingViewModel
            btnSendReview.setOnClickListener {
                starRatingViewModel.postReview(rating)
            }
        }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }

    override fun initBind() {

    }
}