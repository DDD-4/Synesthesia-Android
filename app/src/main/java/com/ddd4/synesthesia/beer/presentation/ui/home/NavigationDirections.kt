package com.ddd4.synesthesia.beer.presentation.ui.home

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.Beer

class NavigationDirections private constructor() {

    companion object {
        fun actionToDetail(beer: Beer): NavDirections = ActionOnlyNavDirections(R.id.action_to_detail)
        fun actionToMyPage(): NavDirections = ActionOnlyNavDirections(R.id.action_to_mypage)
    }
}