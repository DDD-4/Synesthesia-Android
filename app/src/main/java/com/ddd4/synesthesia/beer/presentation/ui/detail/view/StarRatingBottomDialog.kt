package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.databinding.LayoutBottomStarRatingBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.ext.showToast
import com.ddd4.synesthesia.beer.presentation.base.BaseBottomSheetDialogFragment
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.StarRatingBottomClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel.StarRatingViewModel
import com.ddd4.synesthesia.beer.util.CustomAlertDialog
import com.ddd4.synesthesia.beer.util.SimpleCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StarRatingBottomDialog :
    BaseBottomSheetDialogFragment<LayoutBottomStarRatingBinding>(R.layout.layout_bottom_star_rating) {

    private val starRatingViewModel by viewModels<StarRatingViewModel>()
    private val beerInfo by lazy { arguments?.get(getString(R.string.key_beer)) as? Beer }
    private var dismiss: (() -> Unit)? = null
    private val minRating = 0.5f

    private val reviewCallback = object : SimpleCallback {
        override fun call(text: String) {
            starRatingViewModel.review.value = text
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SimpleCallback.callback = reviewCallback
        isCancelable = false
        binding.apply {
            vm = starRatingViewModel
            beer = beerInfo
            beerInfo?.let {
                starRatingViewModel.review.value = it.reviewOwner?.content
                starRatingViewModel.rating.value = it.reviewOwner?.ratio
            }
            btnSendReview.setOnClickListener {
                beerInfo?.let { beer ->
                    starRatingViewModel.postReview(beer.id)
                } ?: kotlin.run { context?.showToast(getString(R.string.review_fail_message)) }
            }
            ivClose.setOnClickListener {
                notice()
            }
        }
        initObserving()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val reviewSheetDialog = BottomSheetDialog(requireContext(), theme)
        reviewSheetDialog.setOnShowListener { dialog ->
            (dialog as? BottomSheetDialog)?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                ?.run {
                    with(BottomSheetBehavior.from(this)) {
                        state = BottomSheetBehavior.STATE_EXPANDED
                        isDraggable = false
                        isHideable = false
                    }
                }
        }

        return reviewSheetDialog

    }

    override fun onDestroy() {
        super.onDestroy()
        SimpleCallback.callback = null
    }

    fun showDialog(fm: FragmentManager, dismiss: (() -> Unit)?) {
        show(fm, "")
        this@StarRatingBottomDialog.dismiss = dismiss
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogStyle
    }

    override fun initBind() {

    }

    override fun initObserving() {
        observeHandledEvent(starRatingViewModel.event.select) {
            handleSelectEvent(it)
        }
        starRatingViewModel.register.observe(viewLifecycleOwner, Observer {
            dismiss()
            dismiss?.invoke()
        })
        starRatingViewModel.rating.observe(viewLifecycleOwner, Observer {
            it?.let { rating ->
                if (rating < minRating) {
                    starRatingViewModel.rating.value = minRating
                }
            } ?: kotlin.run { starRatingViewModel.rating.value = 0.5f }
        })
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            StarRatingBottomClickEntity.ClickGuide -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.please_wait_for_a_little_while),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
    }

    private fun notice() {
        if (beerInfo?.reviewOwner?.ratio == starRatingViewModel.rating.value && beerInfo?.reviewOwner?.content == starRatingViewModel.review.value) {
            dismiss()
        } else {
            CustomAlertDialog(
                title = getString(R.string.page_out),
                message = getString(R.string.do_not_save),
                posivie = getString(R.string.yes),
                negative = getString(R.string.no),
                result = DialogInterface.OnClickListener { dialog, which -> dismiss() }
            ).show(parentFragmentManager, null)
        }
    }
}