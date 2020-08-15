package com.ddd4.synesthesia.beer.domain.repository

import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.AccessTokenInfo
import com.kakao.sdk.user.model.User

interface LoginRepository {
    fun tokenInfo(tokenInfo : (AccessTokenInfo?) -> Unit)
    fun login(userInfo : (User?) -> Unit)
    fun logout(isSuccess : (Boolean) -> Unit)
    fun unlink(isSuccess: (Boolean) -> Unit)
}