package com.ddd4.synesthesia.beer.data.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("result")
    val result : Result
)