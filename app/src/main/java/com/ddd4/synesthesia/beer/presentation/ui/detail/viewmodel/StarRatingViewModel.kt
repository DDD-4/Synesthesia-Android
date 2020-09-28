package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber


class StarRatingViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository
) : BaseViewModel() {

    val register = SingleLiveEvent<Boolean>()
    val review = MutableLiveData<String>()
    val rating = MutableLiveData<Float?>(0.5f)

    init {

    }

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable)
    }

    fun postReview(id : Int) {
        viewModelScope.launch(handler) {
            beerRepository.postReview(id,rating.value ?: 0.5f, review.value ?: "")
            register.call()
        }
    }
}