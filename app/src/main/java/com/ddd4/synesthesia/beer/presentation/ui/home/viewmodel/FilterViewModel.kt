package com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.MutableLiveDataList
import com.ddd4.synesthesia.beer.util.filter.BeerFilter
import com.ddd4.synesthesia.beer.util.filter.FilterSetting

class FilterViewModel @ViewModelInject constructor(
    private val filterSetting: FilterSetting
) : BaseViewModel() {

    val styleSelectedList: MutableLiveDataList<String> = MutableLiveDataList(
        filterSetting.beerFilter.styleFilter ?: mutableListOf()
    )

    val aromaSelectedList: MutableLiveDataList<String> = MutableLiveDataList(
        filterSetting.beerFilter.aromaFilter ?: mutableListOf()
    )

    var abvSelectedRange: MutableLiveData<Pair<Int, Int>> = MutableLiveData(
        filterSetting.beerFilter.abvFilter ?: 0 to 10
    )

    val countrySelectedList: MutableLiveDataList<String> = MutableLiveDataList(
        filterSetting.beerFilter.countryFilter ?: mutableListOf()
    )

    fun executeFiltering() {
        filterSetting.beerFilter = BeerFilter(
            styleSelectedList.value,
            aromaSelectedList.value,
            abvSelectedRange.value,
            countrySelectedList.value
        )
    }

    // TODO 사전 정의를 해놓을 것인지 서버에서 받을 것인지?
    val styleList = mutableListOf(
        "TEST_STYLE_0",
        "TEST_STYLE_1",
        "TEST_STYLE_2",
        "TEST_STYLE_3",
        "TEST_STYLE_4",
        "TEST_STYLE_5",
        "TEST_STYLE_6",
        "TEST_STYLE_7"
    )

    val aromaList = mutableListOf(
        "TEST_AROMA_0",
        "TEST_AROMA_1",
        "TEST_AROMA_2",
        "TEST_AROMA_3",
        "TEST_AROMA_4",
        "TEST_AROMA_5",
        "TEST_AROMA_6",
        "TEST_AROMA_7"
    )

    private val _countryList = MutableLiveData<List<String>>(
        mutableListOf(
            "TEST_COUNTRY_0",
            "TEST_COUNTRY_1",
            "TEST_COUNTRY_2",
            "TEST_COUNTRY_3",
            "TEST_COUNTRY_4",
            "TEST_COUNTRY_5",
            "TEST_COUNTRY_6",
            "TEST_COUNTRY_7",
            "TEST_COUNTRY_8",
            "TEST_COUNTRY_9"
        )
    )
    val countryList: LiveData<List<String>>
        get() = _countryList

}