package com.ddd4.synesthesia.beer.util.sort

import android.content.Context
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.util.SharedPreferenceProvider
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

enum class SortType {
    Default,
    Rating,
    Review
}

class SortImpl(val preference: SharedPreferenceProvider, context: Context) : SortSetting,
    ReadWriteProperty<Any, SortType> {

    private val resource = context.resources

    private fun getDefaultValue(): SortType {
        val storeValue = preference.getPreferenceString(resource.getString(R.string.key_sort_type))
        return if (storeValue.isNullOrEmpty()) SortType.Default else SortType.valueOf(storeValue)
    }

    private val channel = ConflatedBroadcastChannel(getDefaultValue())

    override fun setValue(thisRef: Any, property: KProperty<*>, value: SortType) {
        preference.setPreference(resource.getString(R.string.key_sort_type), value.toString())
        channel.offer(value)
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): SortType = getDefaultValue()

    override var sortType: SortType by this

    override fun getSort(): Flow<SortType> = channel.asFlow()

}