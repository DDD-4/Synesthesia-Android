package com.ddd4.synesthesia.beer.presentation.ui.splash.view

import android.os.Bundle
import androidx.activity.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivitySplashBinding
import com.ddd4.synesthesia.beer.ext.start
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.login.view.LoginActivity
import com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel.LoginViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel.tokenInfo {
//            Timber.tag("tokenInfo").w("splash token : ${it?.accessToken}")
//            Timber.tag("tokenInfo").w("prefs token : ${preference.getPreferenceString(getString(R.string.key_token))}")
            startLogin()
        }
    }

    override fun onBackPressed() {
        // Nothing
    }

    private fun startLogin() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)
            if(preference.getPreferenceString(getString(R.string.key_token)).isNullOrEmpty()) {
                start<LoginActivity>(isFinish = true, isAnimation = true)
            } else {
                start<MainActivity>(isFinish = true, isAnimation = true)
            }
            finish()
        }
    }
}