package com.ddd4.synesthesia.beer.presentation.ui.home

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.Beer
import java.io.Serializable

class NavigationDirections private constructor() {

    @Suppress("CAST_NEVER_SUCCEEDS")
    private data class ActionToDetail(
        val beer: Beer
    ) : NavDirections {

        override fun getArguments(): Bundle {
            val result = Bundle()
            when {
                Parcelable::class.java.isAssignableFrom(Beer::class.java) -> result.putParcelable(
                    "Beer",
                    this.beer as Parcelable
                )
                Serializable::class.java.isAssignableFrom(Beer::class.java) -> result.putSerializable(
                    "Beer",
                    this.beer as Serializable
                )
                else -> throw UnsupportedOperationException(Beer::class.java.name)
            }
            return result
        }

        override fun getActionId() = R.id.action_to_detail

    }


    companion object {
        fun actionToDetail(beer: Beer): NavDirections = ActionToDetail(beer)

        fun actionToMyPage(): NavDirections = ActionOnlyNavDirections(R.id.action_to_mypage)
    }
}