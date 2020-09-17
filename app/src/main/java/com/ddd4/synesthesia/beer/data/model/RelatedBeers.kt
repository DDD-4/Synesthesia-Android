package com.ddd4.synesthesia.beer.data.model

import com.google.gson.annotations.SerializedName

data class RelatedBeers(
    @SerializedName("aroma_related")
    val related: List<Related>,
    @SerializedName("randomly_related")
    val randomlyRelated: List<Related>,
    @SerializedName("style_related")
    val styleRelated: List<Related>
)