package com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.filter.BeerFilter
import com.ddd4.synesthesia.beer.util.filter.FilterSetting

class FilterViewModel @ViewModelInject constructor(
    private val filterSetting: FilterSetting
) : BaseViewModel() {

    val styleSelectedList: MutableList<String> =
        filterSetting.beerFilter.styleFilter?.toMutableList() ?: mutableListOf()
    val aromaSelectedList: MutableList<String> =
        filterSetting.beerFilter.aromaFilter?.toMutableList() ?: mutableListOf()

    fun executeFiltering() {
        filterSetting.beerFilter = BeerFilter(
            styleSelectedList,
            aromaSelectedList,
            null,
            null
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
            "미국",
            "독일",
            "중국",
            "일본",
            "한국",
            "이탈리아",
            "멕시코",
            "브라질",
            "태국",
            "인도",
            "프랑스",
            "영국",
            "스위스"
        )
    )
    val countryList: LiveData<List<String>>
        get() = _countryList

}