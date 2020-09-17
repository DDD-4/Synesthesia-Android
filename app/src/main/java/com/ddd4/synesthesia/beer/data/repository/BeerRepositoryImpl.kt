package com.ddd4.synesthesia.beer.data.repository

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.model.RateOwner
import com.ddd4.synesthesia.beer.data.model.Response
import com.ddd4.synesthesia.beer.data.model.Review
import com.ddd4.synesthesia.beer.data.source.remote.service.BeerApi
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.util.sort.SortType
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(
    private val beerApi : BeerApi
): BeerRepository {

    override suspend fun getBeerList(): List<Beer>? {
        return beerApi.getBeerList()?.result?.beers?.let {
            it
        } ?: kotlin.run {
            getBeerList(SortType.Default)
        }

    }

    override suspend fun getBeer(id : Int): Beer? {
        return beerApi.getBeer(id)?.result?.beer?.let {
            it
        } ?: kotlin.run { getBeerDetail() }
    }

    override suspend fun getUserInfo(): Response? {
        return beerApi.getUserInfo()
    }

    override fun getBeerList(sortType: SortType): List<Beer>? {
        val list = arrayListOf<Beer>()
        when (sortType) {
            SortType.Review -> {
                for (i in 1..3) {
                    list.add(
                        Beer(
                            4.0,
                            arrayListOf("Grape", "Tropical"),
                            "스타일",
                            "브루어리",
                            arrayListOf(),
                            "대한민국(${i})",
                            i,
                            arrayListOf(),
                            "리뷰 정렬(${i})",
                            4.5,
                            RateOwner(0, 0.0, 0)
                        )
                    )
                }
                return list
            }
            SortType.Comment -> {
                for (i in 1..6) {
                    list.add(
                        Beer(
                            4.0,
                            arrayListOf("Grape"),
                            "스타일",
                            "브루어리",
                            arrayListOf(),
                            "대한민국(${i})",
                            i,
                            arrayListOf(),
                            "후기 정렬(${i})",
                            3.0,
                            RateOwner(0, 0.0, 0)
                        )
                    )
                }
                return list
            }
            SortType.Default -> {
                for (i in 1..20) {
                    list.add(
                        Beer(
                            4.0,
                            arrayListOf("Lemon", "Chocolate"),
                            "스타일",
                            "브루어리",
                            arrayListOf(),
                            "대한민국(${i})",
                            i,
                            arrayListOf(),
                            "맥주이름(${i})",
                            5.0,
                            RateOwner(0, 0.0, 0)
                        )
                    )
                }
                return list
            }
        }
    }
    override fun getBeerDetail(): Beer? {
        val reviews = arrayListOf<Review>()
        for (i in 0..20) {
            reviews.add(
                Review(
                    "공감각",
                    5.0f,
                    "2020. 08. 17",
                    "아 맥주.. 제 인생 맥주입니다 ㅠㅠ 너무 맛있어요ㅠㅠㅠ 저는 과일향 나는, 가볍게 마실 수 있는 맥주를 선호하는데, 그런저에게 딱인 것 같습니다! 상큼한 향이 너무 좋았어요"
                )
            )
        }
        return Beer(
            4.0,
            arrayListOf("Grape", "Tropical", "Orange", "Lime", "Lemon"),
            "스타일",
            "브루어리",
            arrayListOf(),
            "대한민국(${0})",
            0,
            arrayListOf(),
            "맥주이름(${0})",
            5.0,
            RateOwner(0,0.0, 0),
            reviews
        )
    }

}