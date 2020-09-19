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

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLogin()
    }

    override fun onBackPressed() {
        // Nothing
    }

    private fun startLogin() {
        // TODO retresh token

        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)
            if(preference.getPreferenceString(getString(R.string.key_token)).isNullOrEmpty()) {
                start<LoginActivity>(true)
            } else {
                start<MainActivity>(true)
            }
            finish()
        }
    }
}