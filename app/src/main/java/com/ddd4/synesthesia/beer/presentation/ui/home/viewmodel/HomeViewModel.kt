package com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository
): BaseViewModel() {
    private val _beerList = MutableLiveData<List<Beer>>()
    val beerList: LiveData<List<Beer>>
        get() = _beerList

    init {
        Timber.d("View Model Initialize: ")
        fetchBeerList()
    }

    private fun fetchBeerList() {
        viewModelScope.launch(Dispatchers.IO) {
            _beerList.postValue(beerRepository.getBeerList())
        }
    }
}