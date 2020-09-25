package com.ddd4.synesthesia.beer.util

interface SimpleCallback {
    companion object {
        var callback : SimpleCallback? = null
    }
    fun call(text : String)
}