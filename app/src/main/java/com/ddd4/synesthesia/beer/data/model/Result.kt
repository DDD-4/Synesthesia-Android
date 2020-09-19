package com.ddd4.synesthesia.beer.data.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("beers")
    val beers: List<Beer>? = null,
    @SerializedName("beer")
    val beer: Beer? = null,
    @SerializedName("related_beers")
    val relatedBeers : RelatedBeers? = null
)