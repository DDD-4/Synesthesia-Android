package com.ddd4.synesthesia.beer.data.source.remote.service

import com.ddd4.synesthesia.beer.data.model.Response
import com.ddd4.synesthesia.beer.data.model.Result
import com.ddd4.synesthesia.beer.data.model.User
import retrofit2.http.*

interface BeerApi {

    /**
     * 맥주 리스트
     */
    @GET("api/beers")
    suspend fun getBeerList(
        @Query("sort_by") sortType: String?,
        @Query("beer_style") style: List<String>?,
        @Query("aroma") aroma: List<String>?,
        @Query("country") country: List<String>?,
        @Query("min_abv") minAbv: Int?,
        @Query("max_abv") maxAbv: Int?,
        @Query("cursor") nextCursor : Int? = 0,
        @Query("max_count") maxCount : Int? = 10
    ): Result<Response>?

    /**
     * 맥주 상세
     */
    @GET("api/beer")
    suspend fun getBeer(
        @Query("beer_id") id: Int
    ): Result<Response>?

    /**
     * 유저 정보
     */
    @GET("api/user")
    suspend fun getUserInfo(): Result<User>?

    /**
     * 유저 정보 업데이트
     */
    @FormUrlEncoded
    @POST("api/user/update")
    suspend fun postUserInfo(
        @Field("nickname") nickName: String?
    )

    /**
     * 리뷰 등록
     */
    @FormUrlEncoded
    @POST("api/review")
    suspend fun postReview(
        @Field("beer_id") id: Int,
        @Field("ratio") ratio: Float,
        @Field("content") content: String?
    )

}