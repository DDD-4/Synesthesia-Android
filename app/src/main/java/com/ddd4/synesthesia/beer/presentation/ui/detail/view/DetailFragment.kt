package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.databinding.FragmentDetailBinding
import com.ddd4.synesthesia.beer.presentation.ui.login.view.LoginActivity
import com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel.LoginViewModel
import com.hyden.ext.start
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener {
            loginViewModel.logout()
        }
        loginViewModel.isLogoutSuccess.observe(this@DetailFragment, Observer {
            if(it) {
                preference.clear()
                start<LoginActivity>(true, bundleOf(Pair(getString(R.string.is_show_snackbar),getString(R.string.success_logout))))
            }
        })
    }
}