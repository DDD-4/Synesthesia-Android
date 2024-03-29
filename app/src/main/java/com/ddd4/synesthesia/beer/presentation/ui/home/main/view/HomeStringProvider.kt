package com.ddd4.synesthesia.beer.presentation.ui.home.main.view

import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.util.provider.StringProvider
import javax.inject.Inject

class HomeStringProvider @Inject constructor(
    private val stringProvider: StringProvider
) {

    enum class Code {
        STYLE,
        AROMA,
        RANDOM,
    }

    fun getStringRes(type: Code): String = when (type) {
        Code.STYLE -> {
            stringProvider.getStringRes(R.string.title_like_style)
        }
        Code.AROMA -> {
            stringProvider.getStringRes(R.string.title_like_aroma)
        }
        Code.RANDOM -> {
            stringProvider.getStringRes(R.string.title_like_random)
        }
    }
}