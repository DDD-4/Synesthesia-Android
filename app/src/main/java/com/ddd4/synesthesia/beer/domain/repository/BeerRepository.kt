package com.ddd4.synesthesia.beer.domain.repository

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.model.Response
import com.ddd4.synesthesia.beer.data.model.Result
import com.ddd4.synesthesia.beer.util.filter.BeerFilter

interface BeerRepository {

    suspend fun getBeerList(sortType: String?, filter: BeerFilter?): List<Beer>?
    suspend fun getUserInfo(): Response?
    suspend fun getBeer(id: Int): Result?
    suspend fun postReview(id: Int, rating: Float, review: String?)

}