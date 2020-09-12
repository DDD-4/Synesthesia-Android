package com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.sort.SortSetting
import com.ddd4.synesthesia.beer.util.sort.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeSortViewModel @ViewModelInject constructor(
    private val sortSetting: SortSetting
) : BaseViewModel() {

    private val _sortType = MutableLiveData<SortType>()
    val sortType: LiveData<SortType>
        get() = _sortType

    init {
        viewModelScope.launch(Dispatchers.IO) {
            sortSetting.getSort().collect {
                _sortType.postValue(it)
            }
        }
    }

    fun sortWithType(sortType: SortType) {
        sortSetting.sortType = sortType
    }

}