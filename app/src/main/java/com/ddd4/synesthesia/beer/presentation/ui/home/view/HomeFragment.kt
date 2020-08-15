package com.ddd4.synesthesia.beer.presentation.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ddd4.synesthesia.beer.BR
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.databinding.FragmentHomeBinding
import com.ddd4.synesthesia.beer.databinding.LayoutHomeContentsBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.BaseItemsApdater
import com.ddd4.synesthesia.beer.presentation.ui.home.NavigationDirections
import com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel.HomeViewModel
import com.ddd4.synesthesia.beer.presentation.ui.login.view.LoginActivity
import com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel.LoginViewModel
import com.ddd4.synesthesia.beer.util.ItemClickListener
import com.hyden.ext.start
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T) {
                Timber.d("onItemClick ${item.toString()}")
                findNavController().navigate(
                    NavigationDirections.actionToDetail(item as Beer)
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contents.set()


    }

    private fun LayoutHomeContentsBinding.set() {
        val listAdapter = BaseItemsApdater(R.layout.item_home, BR.item, itemClickListener)

        with(itemList) {
            adapter = listAdapter
        }

        homeViewModel.beerList.observe(viewLifecycleOwner, Observer {
            listAdapter.updateItems(it)
        })
    }
}