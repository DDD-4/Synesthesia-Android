package com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.MutableLiveDataList
import com.ddd4.synesthesia.beer.util.filter.BeerFilter
import com.ddd4.synesthesia.beer.util.filter.FilterSetting
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class FilterViewModel @ViewModelInject constructor(
    private val filterSetting: FilterSetting
) : BaseViewModel() {

    val styleList = mutableListOf<String>()
    val aromaList = mutableListOf<String>()
    val countryList = mutableListOf<String>()
    var minAbv = 0
    var maxAbv = 15

    val styleSelectedList: MutableLiveDataList<String> = MutableLiveDataList(
        filterSetting.beerFilter.styleFilter ?: mutableListOf()
    )

    val aromaSelectedList: MutableLiveDataList<String> = MutableLiveDataList(
        filterSetting.beerFilter.aromaFilter ?: mutableListOf()
    )

    var abvSelectedRange: MutableLiveData<Pair<Int, Int>?> = MutableLiveData(
        filterSetting.beerFilter.abvFilter
    )

    val countrySelectedList: MutableLiveDataList<String> = MutableLiveDataList(
        filterSetting.beerFilter.countryFilter ?: mutableListOf()
    )

    fun resetAllAsync(): Deferred<Unit> {
        return viewModelScope.async {
            styleSelectedList.clear()
            aromaSelectedList.clear()
            abvSelectedRange.value = null
            countrySelectedList.value = mutableListOf()
        }
    }

    fun executeFiltering() {
        filterSetting.beerFilter = BeerFilter(
            styleSelectedList.value,
            aromaSelectedList.value,
            abvSelectedRange.value,
            countrySelectedList.value
        )
    }

}