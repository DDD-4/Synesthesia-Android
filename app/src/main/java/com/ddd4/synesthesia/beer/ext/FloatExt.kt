package com.ddd4.synesthesia.beer.ext

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

fun Float.toPx(context : Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    context.resources.displayMetrics
).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()