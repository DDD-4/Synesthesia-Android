package com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.domain.repository.LoginRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.SharedPreferenceProvider
import com.ddd4.synesthesia.beer.util.SingleLiveEvent
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class LoginViewModel @ViewModelInject constructor(
    private val loginRepository : LoginRepository,
    private val preference : SharedPreferenceProvider
) : BaseViewModel() {

    val isLoginSuccess = SingleLiveEvent<Pair<User?,Throwable?>>()
    val isLogoutSuccess = SingleLiveEvent<Boolean>()

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable)
    }

    fun tokenInfo(tokenInfo : ((OAuthToken?) -> Unit)? = null) = loginRepository.tokenInfo {
        Timber.tag("tokenInfo").d("kakao token info : ${it?.accessToken}")
        tokenInfo?.invoke(it)
    }

    fun accessToken(code : String?) {
        viewModelScope.launch(handler) {
            loginRepository.accessToken(code) {
                it?.run {
                    TokenManagerProvider.currentManager.setToken(
                        OAuthToken(accessToken, Date(expiresIn.toLong()),refreshToken,Date(refreshTokenExpiresIn.toLong()),scope.split(","))
                    )
                    accessToken.let { token ->
                        preference.setPreference("token",token)
                        login()
                    }
                }
            }

        }
    }

    fun login() = loginRepository.login { user, error ->
        user?.let {
            isLoginSuccess.call(Pair(user,null))
        } ?: kotlin.run { isLoginSuccess.call(Pair(null,error)) }
    }

    fun logout() = loginRepository.logout {
        isLogoutSuccess.call(it)
    }

    fun unlink() = loginRepository.unlink {
        isLogoutSuccess.call(it)
    }
}