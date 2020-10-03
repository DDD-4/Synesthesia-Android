package com.ddd4.synesthesia.beer.presentation.ui.mypage.view

import android.os.Bundle
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityWriteNicknameBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.util.SimpleCallback

class WriteNickNameActivity : BaseActivity<ActivityWriteNicknameBinding>(R.layout.activity_write_nickname) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val text = intent.extras?.get(getString(R.string.key_nickname)) as? String
        binding.apply {
            edtNickname.apply {
                contents = text
                requestFocus()
            }
            ivClose.setOnClickListener { finish() }
            ivClear.setOnClickListener { contents = "" }
            btnSave.setOnClickListener {
                SimpleCallback.callback?.call(edtNickname.text.toString())
                finish()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.apply {
            edtNickname.setSelection(edtNickname.text.length)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}