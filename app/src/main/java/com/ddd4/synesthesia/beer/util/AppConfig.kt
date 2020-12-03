package com.ddd4.synesthesia.beer.util

import android.content.Context
import android.content.pm.PackageManager
import com.ddd4.synesthesia.beer.R
import javax.inject.Inject

class AppConfig @Inject constructor(
    private val context: Context,
    private val sharedPreference : SharedPreferenceProvider) {


    val version
        get() = try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }

    val recentlyVisitTime
        get() = sharedPreference.getPreferenceString(context.resources.getString(R.string.key_recently_visit))
}