package com.ddd4.synesthesia.beer.data.repository

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.model.Response
import com.ddd4.synesthesia.beer.data.model.Result
import com.ddd4.synesthesia.beer.data.model.Review
import com.ddd4.synesthesia.beer.data.model.User
import com.ddd4.synesthesia.beer.data.source.remote.service.BeerApi
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.util.filter.BeerFilter
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(

    private val beerApi: BeerApi
) : BeerRepository {

    override suspend fun getBeerList(sortType: String?, filter: BeerFilter?, cursor : Int?): Response? {
        return beerApi.getBeerList(
            sortType,
            filter?.styleFilter,
            filter?.aromaFilter,
            filter?.countryFilter,
            filter?.abvFilter?.first,
            filter?.abvFilter?.second,
            cursor
        )?.result
    }

    override suspend fun getBeer(id: Int): Response? {

        return beerApi.getBeer(id)?.result?.let {
            it
        }
    }

    override suspend fun getUserInfo(): User? {
        return beerApi.getUserInfo()?.result?.let {
            it
        }
    }

    override suspend fun postUserInfo(nickName: String?) {
        return beerApi.postUserInfo(nickName)
    }

    override suspend fun postReview(id: Int, rating: Float, review: String?) {
        return beerApi.postReview(id, rating, review)
    }

    override suspend fun getReview() : List<Result<Review>> {
        return beerApi.getReview()
    }
}