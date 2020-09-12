package com.ddd4.synesthesia.beer.util.sort

import kotlinx.coroutines.flow.Flow

interface SortSetting {

    var sortType: SortType
    fun getSort(): Flow<SortType>

}