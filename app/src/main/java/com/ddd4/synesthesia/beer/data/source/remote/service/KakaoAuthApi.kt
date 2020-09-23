package com.ddd4.synesthesia.beer.data.source.remote.service

import com.ddd4.synesthesia.beer.data.source.response.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface KakaoAuthApi {

    /**
     * 토큰
     */
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun accessToken(
        @Field("grant_type") grantType : String? = "authorization_code",
        @Field("client_id") clientId : String? = "0a1bd7290e3c405bd386a061e567c432",
        @Field("redirect_uri") redirectUrl : String? = "kakao0a1bd7290e3c405bd386a061e567c432://oauth",
        @Field("code") code : String? = "",
        @Field("client_secret") secret : String? = "Ldg6jTcHIJazwMDJmVzlLkZqlD5LGNiy"
    ) : TokenResponse
}