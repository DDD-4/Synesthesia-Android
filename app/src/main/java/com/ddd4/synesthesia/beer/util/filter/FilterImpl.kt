package com.ddd4.synesthesia.beer.util.filter

import android.content.Context
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.util.SharedPreferenceProvider
import com.google.gson.Gson
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class FilterImpl(
    val preference: SharedPreferenceProvider,
    context: Context
) : FilterSetting, ReadWriteProperty<Any, BeerFilter> {

    private val key = context.resources.getString(R.string.key_filter)
    private val channel = ConflatedBroadcastChannel(getDefaultValue())

    override var beerFilter: BeerFilter by this

    override fun getBeerFilterFlow(): Flow<BeerFilter> = channel.asFlow()

    private fun getDefaultValue(): BeerFilter {
        val storeValue = preference.getPreferenceString(key)
        return Gson().fromJson(storeValue, BeerFilter::class.java) ?: BeerFilter()
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: BeerFilter) {
        preference.setPreference(key, Gson().toJson(value))
        channel.offer(value)
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): BeerFilter = getDefaultValue()

}