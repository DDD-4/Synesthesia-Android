package com.ddd4.synesthesia.beer.util.filter

data class BeerFilter(
    val styleFilter: List<String>?,
    val aromaFilter: List<String>?,
    val abvFilter: Pair<Int, Int>?,
    val countryFilter: List<String>?
)