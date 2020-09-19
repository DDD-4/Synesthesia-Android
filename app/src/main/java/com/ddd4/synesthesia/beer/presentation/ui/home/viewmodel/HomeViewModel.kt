package com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.sort.SortSetting
import com.ddd4.synesthesia.beer.util.sort.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository,
    private val sortSetting: SortSetting
) : BaseViewModel() {

    private val _beerList = MutableLiveData<List<Beer>?>()
    val beerList: LiveData<List<Beer>?>
        get() = _beerList

    private val _sortType = MutableLiveData<SortType>()
    val sortType: LiveData<SortType>
        get() = _sortType

    init {
        viewModelScope.launch(Dispatchers.IO) {
            sortSetting.getSort().collect {
                _beerList.postValue(beerRepository.getBeerList(it))
                _sortType.postValue(it)
            }
        }

    }

}