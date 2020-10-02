package com.ddd4.synesthesia.beer.data.model

import com.google.gson.annotations.SerializedName

data class Result<ELEMENT>(
    @SerializedName("result")
    val result : ELEMENT?
)