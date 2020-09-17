package com.ddd4.synesthesia.beer.data.source.remote.service

import com.ddd4.synesthesia.beer.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerApi {

    /**
     * 맥주 리스트
     */
    @GET("api/beers")
    suspend fun getBeerList() : Response?

    /**
     * 맥주 상세
     */
    @GET("api/beer")
    suspend fun getBeer(
        @Query("beer_id") id : Int
    ) : Response?

    /**
     * 유저 정보
     */
    @GET("api/user")
    suspend fun getUserInfo() : Response?
}