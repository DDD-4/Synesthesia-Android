package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel


class StarRatingViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository
) : BaseViewModel() {

    val review = MutableLiveData<String>()


    init {

    }

    fun postReview(rate : Float) {
//        beerRepository.postReview(rate,review.value)
    }
}