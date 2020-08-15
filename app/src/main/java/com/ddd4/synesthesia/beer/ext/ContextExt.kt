package com.ddd4.synesthesia.beer.ext

import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast

fun Context.versionName() : String =
    packageManager.getPackageInfo(packageName,0).versionName


fun Context.showKeyboard(view: View) {
    view.run {
        when (view) {
            is EditText -> {
                isFocusable = true
                isFocusableInTouchMode = true
                view.requestFocus()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                view.setSelection(view.length())

            }
        }
    }
}

fun Context.hideKeyboard(view: View) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
        view.windowToken,
        0
    )
}

fun Context.permissonsCheck(
    neededPermissions: Array<String>,
    granted : (() -> Unit)? = null,
    denied : (() -> Unit)? = null
) {
    for(permission in neededPermissions) {
        if(checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            denied?.invoke()
            return
        }
    }
    granted?.invoke()
}

fun Context.showToast(message : String, duration: Int? = null) {
    Toast.makeText(this,message, duration ?: Toast.LENGTH_SHORT).show()
}