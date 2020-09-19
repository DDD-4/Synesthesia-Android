package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.os.Bundle
import androidx.navigation.NavArgs
import com.ddd4.synesthesia.beer.data.model.Beer

data class DetailFragmentArgs(
    val beer: Beer
) : NavArgs {

    companion object {
        @JvmStatic
        fun fromBundle(bundle: Bundle): DetailFragmentArgs {
            bundle.classLoader = DetailFragmentArgs::class.java.classLoader

            val _beer: Beer?
            if (bundle.containsKey("Beer")) {
                _beer = bundle.get("Beer") as Beer?

                if (_beer == null)
                    throw IllegalArgumentException()

            } else {
                throw IllegalArgumentException()
            }
            return DetailFragmentArgs(_beer)
        }
    }
}