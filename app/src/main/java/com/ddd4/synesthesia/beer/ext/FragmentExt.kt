package com.hyden.ext

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ddd4.synesthesia.beer.ext.permissonsCheck
import com.ddd4.synesthesia.beer.util.Consts.DEF_REQUEST_PERMISSION_CODE

inline fun <reified T> Fragment.start(isFinish : Boolean? = false, bundle : Bundle? = null) {
    context?.run {
        Intent(this, T::class.java).apply {
            bundle?.let {
                putExtras(bundle)
            }
            startActivity(this)
            if(isFinish == true) {
                activity?.finish()
            }
        }
    }
}


fun Fragment.permissonsCheck(
    neededPermissions: Array<String>,
    granted : (() -> Unit)? = null
) {
    context?.permissonsCheck(
        neededPermissions = neededPermissions,
        granted = {
            granted?.invoke()
        },
        denied = {
            requestPermissions(neededPermissions,DEF_REQUEST_PERMISSION_CODE)
        }
    )
}