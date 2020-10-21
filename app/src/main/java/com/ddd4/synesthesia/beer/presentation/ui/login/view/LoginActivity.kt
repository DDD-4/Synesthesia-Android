package com.ddd4.synesthesia.beer.presentation.ui.login.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityLoginBinding
import com.ddd4.synesthesia.beer.ext.showSnackBar
import com.ddd4.synesthesia.beer.ext.showToast
import com.ddd4.synesthesia.beer.ext.start
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel.LoginViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.view.MainActivity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel by viewModels<LoginViewModel>()
    private val callback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.tag("tokenInfo").e(error)
            showToast(getString(R.string.fail_login))
        } else if (token != null) {
            preference.setPreference(getString(R.string.key_token),token.accessToken)
            loginViewModel.login()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra(getString(R.string.is_show_snackbar))?.let {
            binding.root.showSnackBar(it)
        }
        intent.data?.let {
            it.query?.split("=")?.let { query ->
                if(query.isNotEmpty()) {
                    loginViewModel.accessToken(query[1])
                }
            }
        }

        binding.ibLogin.setOnClickListener {
            startLogin()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun startLogin() {
        // 카카오톡으로 로그인
        LoginClient.instance.apply {
            if(isKakaoTalkLoginAvailable(this@LoginActivity)) {
                loginWithKakaoTalk(context = this@LoginActivity, callback = callback)
            } else {
                loginWithKakaoAccount(this@LoginActivity, callback = callback)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun initObserving() {
        loginViewModel.isLoginSuccess.observe(this@LoginActivity, Observer {
            it.first?.let {
                start<MainActivity>(true)
            } ?: kotlin.run {
                showToast("${getString(R.string.fail_login)}\n${it.second?.message}")
                preference.remove(getString(R.string.key_token))
            }
        })

    }
}