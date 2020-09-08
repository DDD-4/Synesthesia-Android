package com.ddd4.synesthesia.beer.presentation.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel.HomeSortViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeSortDialog
    : BottomSheetDialogFragment() {

    // ViewModel
    private val viewModel: HomeSortViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_bottom_filter, container, false)
    }


    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }

    // 들어온 값에 따라, Bold Font 처리 필요
    // 선택한 값에 따라 Fetch

}