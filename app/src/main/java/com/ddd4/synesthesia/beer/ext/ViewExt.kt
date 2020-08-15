package com.ddd4.synesthesia.beer.ext

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar


fun View.showSnackBar(message : String, duration: Int? = null) {
    Snackbar.make(this, message, duration ?: Snackbar.LENGTH_SHORT).also { snackbar ->
        snackbar.view.apply {
            val view = findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            view.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snackbar.show()
        }
    }
}