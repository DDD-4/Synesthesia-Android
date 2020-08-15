package com.ddd4.synesthesia.beer.presentation.ui.login.view

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
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra(getString(R.string.is_show_snackbar))?.let {
            binding.root.showSnackBar(it)
        }
        binding.btnLogin.setOnClickListener {
            startLogin()
        }
    }

    private fun startLogin() {
        // 카카오톡으로 로그인
        LoginClient.instance.loginWithKakaoTalk(this@LoginActivity) { token, error ->
            if (error != null) {
                Timber.tag("login").e(error)
                showToast(getString(R.string.fail_login))
            } else if (token != null) {
                preference.setPreference(getString(R.string.key_token),token.accessToken)
                loginViewModel.login()
            }
        }
    }

    override fun initObserving() {
        super.initObserving()
        loginViewModel.isLoginSuccess.observe(this@LoginActivity, Observer { user ->
            user?.let {
                start<MainActivity>(true,bundleOf(Pair(getString(R.string.key_user),it)))
            } ?: kotlin.run { preference.remove(getString(R.string.key_token)) }
        })

    }
}