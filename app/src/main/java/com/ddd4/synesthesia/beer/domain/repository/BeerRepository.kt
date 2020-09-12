package com.ddd4.synesthesia.beer.domain.repository

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.util.sort.SortType

interface BeerRepository {
    fun getBeerList(sortType: SortType): List<Beer>
    suspend fun getBeer(): Beer
}