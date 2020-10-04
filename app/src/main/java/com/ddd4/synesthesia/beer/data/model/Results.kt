package com.ddd4.synesthesia.beer.data.model

import com.google.gson.annotations.SerializedName

data class Results<ELEMENT>(
    @SerializedName("result")
    val results : List<ELEMENT>?
)