package com.ddd4.synesthesia.beer.data.repository

import com.ddd4.synesthesia.beer.domain.repository.LoginRepository
import com.ddd4.synesthesia.beer.util.SharedPreferenceProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.AccessTokenInfo
import com.kakao.sdk.user.model.User

class LoginRepositoryImpl(val preference : SharedPreferenceProvider) : LoginRepository {

    override fun tokenInfo(tokenInfo : (AccessTokenInfo?) -> Unit) {
        UserApiClient.instance.accessTokenInfo { token, error ->
            if (error != null) {
                tokenInfo.invoke(null)
                preference.remove("token")
            } else if (token != null) {
                preference.setPreference("token",token)
                tokenInfo.invoke(token)
            }
        }
    }

    override fun login(userInfo : (User?) -> Unit) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                userInfo.invoke(null)
            } else if (user != null) {
                userInfo.invoke(user)
            }
        }
    }


    override fun logout(isSuccess: (Boolean) -> Unit) {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                isSuccess.invoke(false)
            }
            else {
                isSuccess.invoke(true)
            }
        }
    }

    override fun unlink(isSuccess: (Boolean) -> Unit) {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                isSuccess.invoke(false)
            }
            else {
                isSuccess.invoke(true)
            }
        }
    }
}