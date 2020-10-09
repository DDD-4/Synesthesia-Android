package com.ddd4.synesthesia.beer.domain.repository

import com.ddd4.synesthesia.beer.data.source.response.TokenResponse
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User

interface LoginRepository {
    fun tokenInfo(tokenInfo : (OAuthToken?) -> Unit)
    suspend fun accessToken(code : String?,accessToken : ((TokenResponse?) -> Unit)? = null) : TokenResponse
    fun login(userInfo : (User?, Throwable?) -> Unit)
    fun logout(isSuccess : (Boolean) -> Unit)
    fun unlink(isSuccess: (Boolean) -> Unit)
    fun me(userInfo: (User?) -> Unit)
}