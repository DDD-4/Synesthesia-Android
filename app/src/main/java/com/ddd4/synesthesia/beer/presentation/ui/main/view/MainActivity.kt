package com.ddd4.synesthesia.beer.presentation.ui.main.view

import android.os.Bundle
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showRecentlyVisitTime()
    }
}