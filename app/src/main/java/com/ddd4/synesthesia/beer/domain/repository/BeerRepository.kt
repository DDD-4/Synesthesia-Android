package com.ddd4.synesthesia.beer.domain.repository

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.model.Response
import com.ddd4.synesthesia.beer.data.model.User
import com.ddd4.synesthesia.beer.util.sort.SortType

interface BeerRepository {
    suspend fun getBeerList(sortType: SortType): List<Beer>?
    suspend fun getUserInfo() : User?
    suspend fun postUserInfo(nickName : String?)
    suspend fun getBeer(id : Int): Response?
    suspend fun postReview(id : Int, rating : Float, review : String?)
}