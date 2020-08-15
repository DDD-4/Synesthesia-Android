package com.ddd4.synesthesia.beer.util

interface ItemClickListener {
    fun <T> onItemClick(item : T)
}

interface ItemLongClickListener {
    fun <T> onItemLongClick(item : T) : Boolean?
}