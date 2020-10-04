package com.ddd4.synesthesia.beer.presentation.ui.review.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.model.Review
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class MyReviewViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository
): BaseViewModel() {

    private val _myReviews = MutableLiveData<List<Review>>()
    val myReviews : LiveData<List<Review>> get() = _myReviews

    init {
        review()
    }

    fun review() {
        viewModelScope.launch {
            val result = (beerRepository.getReview())
            _myReviews.postValue(result.results)
        }
    }
    override fun onCleared() {
        super.onCleared()
    }
}