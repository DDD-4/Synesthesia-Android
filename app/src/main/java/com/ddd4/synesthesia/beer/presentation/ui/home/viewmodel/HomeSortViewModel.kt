package com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.sort.SortSetting
import com.ddd4.synesthesia.beer.util.sort.SortType

class HomeSortViewModel @ViewModelInject constructor(
    private val sortSetting: SortSetting
) : BaseViewModel() {

    val sortType: LiveData<SortType> = sortSetting.getSort().asLiveData()

    fun sortWithType(sortType: SortType) {
        sortSetting.sortType = sortType
    }

}