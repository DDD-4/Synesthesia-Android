package com.ddd4.synesthesia.beer.util

import androidx.lifecycle.MutableLiveData

class MutableLiveDataList<T> constructor() : MutableLiveData<MutableList<T>>() {

    constructor(elements: Collection<T>) : this() {
        value = elements.toMutableList()
    }

    init {
        value = mutableListOf()
    }

    fun add(item: T) {
        val items: MutableList<T> = value ?: mutableListOf()
        items.add(item)
        value = items
    }

    fun remove(item: T) {
        val items: MutableList<T> = value ?: mutableListOf()
        items.remove(item)
        value = items
    }

    fun isNotEmpty(): Boolean {
        val items: MutableList<T> = value ?: return false
        return items.isNotEmpty()
    }

    fun contains(item: T): Boolean {
        val items: MutableList<T> = value ?: return false
        return items.contains(item)
    }

    operator fun get(index: Int): T {
        return value?.get(index)
            ?: throw IndexOutOfBoundsException("Empty list doesn't contain element at index $index.")
    }

    fun count(): Int {
        return value?.count() ?: 0
    }

    fun clear() {
        val items: MutableList<T> = value ?: return
        items.clear()
    }


}