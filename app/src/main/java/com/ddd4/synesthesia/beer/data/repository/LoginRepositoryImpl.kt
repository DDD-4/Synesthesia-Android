package com.ddd4.synesthesia.beer.data.repository

import com.ddd4.synesthesia.beer.data.source.remote.service.KakaoApi
import com.ddd4.synesthesia.beer.data.source.remote.service.KakaoAuthApi
import com.ddd4.synesthesia.beer.data.source.response.TokenResponse
import com.ddd4.synesthesia.beer.domain.repository.LoginRepository
import com.ddd4.synesthesia.beer.util.SharedPreferenceProvider
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User

class LoginRepositoryImpl(
    private val kakaoApi: KakaoApi,
    private val kakaoAuthApi: KakaoAuthApi,
    private val preference: SharedPreferenceProvider
) : LoginRepository {

    override fun tokenInfo(tokenInfo: (OAuthToken?) -> Unit) {
        UserApiClient.instance.accessTokenInfo { token, error ->
            if (error != null) {
                tokenInfo.invoke(null)
                preference.remove("token")
            } else if (token != null) {
                preference.setPreference("token",TokenManagerProvider.currentManager.getToken()?.accessToken)
                tokenInfo.invoke(TokenManagerProvider.currentManager.getToken())
            }
        }
    }

    override fun login(userInfo: (User?, Throwable?) -> Unit) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                userInfo.invoke(null,error)
            } else if (user != null) {
                userInfo.invoke(user,null)
            }
        }
    }


    override fun logout(isSuccess: (Boolean) -> Unit) {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                isSuccess.invoke(false)
            } else {
                isSuccess.invoke(true)
            }
        }
    }

    override fun unlink(isSuccess: (Boolean) -> Unit) {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                isSuccess.invoke(false)
            } else {
                isSuccess.invoke(true)
            }
        }
    }

    override fun me(userInfo: (User?) -> Unit) {
        UserApiClient.instance.me { user, error ->
            userInfo.invoke(user)
        }

    }

    override suspend fun accessToken(
        code: String?,
        accessToken : ((TokenResponse?) -> Unit)?
    ): TokenResponse {
        val token = kakaoAuthApi.accessToken(code = code)
        accessToken?.invoke(token)
        return token
    }
}