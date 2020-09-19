package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.model.Result
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository
) : BaseViewModel() {

    private val _result = MutableLiveData<Result>()
    val result : LiveData<Result> get() = _result

    init {
    }

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable)
        _result.value = beerRepository.getBeerDetail()
    }

    fun fetchBeer(id : Int) {
        viewModelScope.launch(handler) {
            _result.postValue(beerRepository.getBeer(id))
        }
    }
}