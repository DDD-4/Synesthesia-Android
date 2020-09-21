package com.ddd4.synesthesia.beer.domain.repository

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.model.Response
import com.ddd4.synesthesia.beer.data.model.Result

interface BeerRepository {
    suspend fun getBeerList(): List<Beer>?
    suspend fun getUserInfo() : Response?
    suspend fun getBeer(id : Int): Result?
    suspend fun postReview(id : Int, rate: Float, review: String?)
}