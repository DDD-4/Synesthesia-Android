package com.ddd4.synesthesia.beer.domain.repository

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.model.Response
import com.ddd4.synesthesia.beer.data.model.User
import com.ddd4.synesthesia.beer.util.filter.BeerFilter

interface BeerRepository {

    suspend fun getBeerList(sortType: String?, filter: BeerFilter?): List<Beer>?
    suspend fun getUserInfo(): User?
    suspend fun postUserInfo(nickName: String?)
    suspend fun getBeer(id: Int): Response?
    suspend fun postReview(id: Int, rating: Float, review: String?)

}