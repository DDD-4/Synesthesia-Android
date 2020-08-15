package com.ddd4.synesthesia.beer.domain.repository

import com.ddd4.synesthesia.beer.data.model.Beer

interface BeerRepository {
    suspend fun getBeerList(): List<Beer>
}