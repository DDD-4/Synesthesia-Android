package com.ddd4.synesthesia.beer.data.source.remote.service

import com.ddd4.synesthesia.beer.data.source.response.LogoutResponse
import com.ddd4.synesthesia.beer.data.source.response.RefreshTokenResponse
import retrofit2.http.POST

interface KakaoApi {

    /**
     * 로그아웃
     */
    @POST("v1/user/logout")
    suspend fun logout() : LogoutResponse

    /**
     * 연결끊기
     */
    @POST("v1/user/unlink")
    suspend fun unlike() : LogoutResponse

    /**
     * 리프레시 토큰
     */
    @POST("oauth/token")
    suspend fun refreshToken() : RefreshTokenResponse
}