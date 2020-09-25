package com.ddd4.synesthesia.beer.util.filter

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface FilterSetting {

    fun getStyleFilterFlow(): Flow<List<String>?>
    fun getAromaFilterFlow(): Flow<List<String>?>
    fun getAbvFilterFlow(): Flow<Pair<Int, Int>?>
    fun getCountryFilterFlow(): Flow<List<String>?>

}

fun FilterSetting.getBeerFilterFlow(): Flow<BeerFilter> {
    return combine(
        getStyleFilterFlow(),
        getAromaFilterFlow(),
        getAbvFilterFlow(),
        getCountryFilterFlow(),
        transform = { styleFilter, aromaFilter, abvFilter, countryFilter ->
            BeerFilter(
                styleFilter, aromaFilter, abvFilter, countryFilter
            )
        }
    )
}