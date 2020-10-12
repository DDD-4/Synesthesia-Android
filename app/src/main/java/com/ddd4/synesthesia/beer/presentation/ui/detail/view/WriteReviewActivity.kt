package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.content.DialogInterface
import android.os.Bundle
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityWriteReviewBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.util.CustomAlertDialog
import com.ddd4.synesthesia.beer.util.SimpleCallback

class WriteReviewActivity : BaseActivity<ActivityWriteReviewBinding>(R.layout.activity_write_review) {

    private val review by lazy { intent.extras?.get(getString(R.string.key_review)) as? String }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            edtReview.apply {
                contents = review
                requestFocus()
            }
            ivClose.setOnClickListener { notice() }
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
        notice()
    }

    private fun notice() {
        if(binding.contents == review) {
            finish()
        } else {
            CustomAlertDialog(
                title = getString(R.string.page_out),
                message = getString(R.string.do_not_save),
                posivie = getString(R.string.yes),
                negative = getString(R.string.no),
                result = DialogInterface.OnClickListener { dialog, which -> finish() }
            ).show(supportFragmentManager,null)
        }
    }
}