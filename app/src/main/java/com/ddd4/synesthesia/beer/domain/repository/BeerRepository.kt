package com.ddd4.synesthesia.beer.domain.repository

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.model.Response
import com.ddd4.synesthesia.beer.util.sort.SortType
import com.ddd4.synesthesia.beer.data.model.Result

interface BeerRepository {
    suspend fun getBeerList(): List<Beer>?
    suspend fun getUserInfo() : Response?
    suspend fun getBeer(id : Int): Result?
    suspend fun postReview(rating : Float, review : String?)

    // sample
    fun getBeerDetail(): Result?
    fun getBeerList(sortType: SortType): List<Beer>?
}