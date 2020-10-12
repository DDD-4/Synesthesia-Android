package com.ddd4.synesthesia.beer.util

enum class ClickType() {
    ITEM
}
interface ItemClickListener {
    fun <T> onItemClick(item : T? = null)
}

interface ItemLongClickListener {
    fun <T> onItemLongClick(item : T? = null) : Boolean?
}