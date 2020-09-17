package com.ddd4.synesthesia.beer.data.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("Beers")
    val beers: List<Beer>,
    @SerializedName("beer")
    val beer: Beer
)