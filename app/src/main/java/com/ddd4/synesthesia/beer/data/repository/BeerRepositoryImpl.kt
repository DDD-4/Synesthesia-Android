package com.ddd4.synesthesia.beer.data.repository

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.model.Response
import com.ddd4.synesthesia.beer.data.model.Result
import com.ddd4.synesthesia.beer.data.source.remote.service.BeerApi
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(
    private val beerApi : BeerApi
): BeerRepository {

    override suspend fun getBeerList(): List<Beer>? {
        return beerApi.getBeerList()?.result?.beers
    }

    override suspend fun getBeer(id : Int): Result? {
        return beerApi.getBeer(id)?.result
    }

    override suspend fun getUserInfo(): Response? {
        return beerApi.getUserInfo()
    }

    override suspend fun postReview(id : Int, rate: Float, review: String?) {
        beerApi.postReview(
            id = id,
            ratio = rate,
            content = review)
    }

}