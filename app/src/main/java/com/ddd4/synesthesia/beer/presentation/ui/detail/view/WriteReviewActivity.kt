package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.os.Bundle
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityWriteReviewBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.util.SimpleCallback

class WriteReviewActivity : BaseActivity<ActivityWriteReviewBinding>(R.layout.activity_write_review) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val text = intent.extras?.get(getString(R.string.key_review)) as? String
        binding.apply {
            edtReview.apply {
                contents = text
                requestFocus()
            }
            ivClose.setOnClickListener { finish() }
            btnSendReview.setOnClickListener {
                SimpleCallback.callback?.call(edtReview.text.toString())
                finish()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.apply {
            edtReview.setSelection(edtReview.text.length)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}