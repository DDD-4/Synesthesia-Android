package com.ddd4.synesthesia.beer.data.repository

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.model.RateOwner
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository

class BeerRepositoryImpl : BeerRepository {

    override suspend fun getBeerList(): List<Beer> {
        val list = arrayListOf<Beer>()
        for (i in 1 .. 20) {
            list.add(
                Beer(
                    4.0,
                    arrayListOf("Grape", "Tropical", "Orange", "Lime", "Lemon"),
                    "스타일",
                    "브루어리",
                    arrayListOf(),
                    "대한민국(${i})",
                    i,
                    arrayListOf(),
                    "맥주이름(${i})",
                    5.0,
                    RateOwner(0,0.0, 0)
                )
            )
        }
        return list
    }

}