package com.ddd4.synesthesia.beer.data.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("beers")
    val beers: List<Beer>?,
    @SerializedName("beer")
    val beer: Beer?,
    @SerializedName("related_beers")
    val relatedBeers : RelatedBeers?
)