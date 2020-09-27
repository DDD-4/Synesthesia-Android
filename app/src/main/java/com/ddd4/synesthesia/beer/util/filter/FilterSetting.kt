package com.ddd4.synesthesia.beer.util.filter

import kotlinx.coroutines.flow.Flow

interface FilterSetting {

    var beerFilter: BeerFilter
    fun getBeerFilterFlow(): Flow<BeerFilter>

    // TODO Filter 개별 저장 할 경우
//    fun getStyleFilterFlow(): Flow<List<String>?>
//    fun getAromaFilterFlow(): Flow<List<String>?>
//    fun getAbvFilterFlow(): Flow<Pair<Int, Int>?>
//    fun getCountryFilterFlow(): Flow<List<String>?>

}
// TODO Filter 개별 저장 할 경우
//fun FilterSetting.getBeerFilterFlow(): Flow<BeerFilter> {
//    return combine(
//        getStyleFilterFlow(),
//        getAromaFilterFlow(),
//        getAbvFilterFlow(),
//        getCountryFilterFlow(),
//        transform = { styleFilter, aromaFilter, abvFilter, countryFilter ->
//            BeerFilter(
//                styleFilter, aromaFilter, abvFilter, countryFilter
//            )
//        }
//    )
//}