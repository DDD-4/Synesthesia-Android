package com.ddd4.synesthesia.beer.domain.repository

import com.ddd4.synesthesia.beer.data.model.*
import com.ddd4.synesthesia.beer.util.filter.BeerFilter

interface BeerRepository {

    suspend fun getBeerList(sortType: String?, filter: BeerFilter?, cursor : Int?): Response?
    suspend fun getUserInfo(): User?
    suspend fun postUserInfo(nickName: String?)
    suspend fun getBeer(id: Int): Response?
    suspend fun getReview() : Results<Review>
    suspend fun postReview(id: Int, rating: Float, review: String?)

}