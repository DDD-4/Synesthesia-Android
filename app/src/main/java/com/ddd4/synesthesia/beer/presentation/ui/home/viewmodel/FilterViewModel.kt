package com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel

class FilterViewModel @ViewModelInject constructor(

) : BaseViewModel() {

    val styleList = arrayListOf(
        "Wheat Beer",
        "Pale Lager",
        "India Pale Ale",
        "Pilsner",
        "Dark Lager",
        "Amber Lager",
        "Porter",
        "Stout"
    )

    val aromaList = arrayListOf(
        "Citrus",
        "Hoppy",
        "Floral",
        "Spicy",
        "Malty",
        "Burnt",
        "Sour",
        "Sweet",
        "Bitter",
        "Linger",
        "Toffee",
        "Herbal"
    )

    private val _countryList = MutableLiveData<List<String>>(
        arrayListOf(
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