package com.ddd4.synesthesia.beer.data.source.remote.service

import com.ddd4.synesthesia.beer.data.model.Response
import com.ddd4.synesthesia.beer.util.sort.SortType
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
        @Query("max_abv") maxAbv: Int?
    ) : Response?

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

    /**
     * 리뷰 등록
     */
    @FormUrlEncoded
    @POST("api/review")
    suspend fun postReview(
        @Field("beer_id") id : Int,
        @Field("ratio") ratio : Float,
        @Field("content") content : String?
    )

}