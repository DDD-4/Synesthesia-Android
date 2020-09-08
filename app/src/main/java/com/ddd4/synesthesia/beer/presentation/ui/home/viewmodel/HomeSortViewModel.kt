package com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel

class HomeSortViewModel @ViewModelInject constructor(
    private val repository: BeerRepository
) : BaseViewModel() {


}