package com.ddd4.synesthesia.beer.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import javax.inject.Inject

class SharedPreferenceProvider @Inject constructor(private val context: Context) {

    private val SHARED_PRIVATE_KEY = "BEER"

    fun <T> setPreference(key: String, value : T) {
        when (value) {
            is String -> {
                context.getSharedPreferences(SHARED_PRIVATE_KEY, MODE_PRIVATE).edit().run { putString(key,value) }.apply()
            }
            is Int -> {
                context.getSharedPreferences(SHARED_PRIVATE_KEY, MODE_PRIVATE).edit().run { putInt(key,value) }.apply()
            }
            is Long -> {
                context.getSharedPreferences(SHARED_PRIVATE_KEY, MODE_PRIVATE).edit().run { putLong(key,value) }.apply()
            }
            is Float -> {
                context.getSharedPreferences(SHARED_PRIVATE_KEY, MODE_PRIVATE).edit().run { putFloat(key,value) }.apply()
            }
            is Boolean -> {
                context.getSharedPreferences(SHARED_PRIVATE_KEY, MODE_PRIVATE).edit().run { putBoolean(key,value) }.apply()
            }
        }
    }

    fun getPreferenceString(key : String) : String? = context.getSharedPreferences(SHARED_PRIVATE_KEY,MODE_PRIVATE)?.getString(key,"") ?: ""
    fun getPreferenceInt(key : String) : Int? = context.getSharedPreferences(SHARED_PRIVATE_KEY,MODE_PRIVATE)?.getInt(key,0) ?: 0
    fun getPreferenceLong(key : String) : Long? = context.getSharedPreferences(SHARED_PRIVATE_KEY,MODE_PRIVATE)?.getLong(key,0) ?: 0L
    fun getPreferenceFloat(key : String) : Float? = context.getSharedPreferences(SHARED_PRIVATE_KEY,MODE_PRIVATE)?.getFloat(key,0f) ?: 0f
    fun getPreferenceBoolean(key : String) : Boolean? = context.getSharedPreferences(SHARED_PRIVATE_KEY,MODE_PRIVATE)?.getBoolean(key,false) ?: false

    fun remove(key : String) {
        context.getSharedPreferences(SHARED_PRIVATE_KEY, MODE_PRIVATE).edit().remove(key).apply()
    }

    fun clear() {
        context.getSharedPreferences(SHARED_PRIVATE_KEY, MODE_PRIVATE).edit().clear().apply()
    }
}