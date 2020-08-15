package com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.ddd4.synesthesia.beer.domain.repository.LoginRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.SingleLiveEvent
import com.kakao.sdk.user.model.User

class LoginViewModel @ViewModelInject constructor(
    private val loginRepository : LoginRepository
) : BaseViewModel() {

    val isLoginSuccess = SingleLiveEvent<User?>()
    val isLogoutSuccess = SingleLiveEvent<Boolean>()

    fun token() = loginRepository.tokenInfo {
        it
    }

    fun login() = loginRepository.login { user ->
        user?.let { isLoginSuccess.call(it) }
    }

    fun logout() = loginRepository.logout {
        isLogoutSuccess.call(it)
    }

    fun unlink() = loginRepository.unlink {
        isLogoutSuccess.call(it)
    }
}