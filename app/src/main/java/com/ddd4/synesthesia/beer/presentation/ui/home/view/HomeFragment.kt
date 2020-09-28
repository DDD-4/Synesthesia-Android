package com.ddd4.synesthesia.beer.presentation.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ddd4.synesthesia.beer.BR
import com.ddd4.synesthesia.beer.HomeNavigationDirections
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.databinding.FragmentHomeBinding
import com.ddd4.synesthesia.beer.databinding.LayoutHomeContentsBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.BaseItemsApdater
import com.ddd4.synesthesia.beer.presentation.ui.home.NavigationDirections
import com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel.HomeViewModel
import com.ddd4.synesthesia.beer.util.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val listAdapter by lazy { BaseItemsApdater(R.layout.item_home, BR.item, itemClickListener) }

    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T?) {
                Timber.d("onItemClick ${item.toString()}")
                findNavController().navigate(HomeNavigationDirections.actionToDetail(item as Beer))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            vm = homeViewModel

            contents.set()
            adapter = listAdapter
            header.btnMyPage.setOnClickListener {
                findNavController().navigate(NavigationDirections.actionToMyPage())
            }

            sort.setOnClickListener {
                val bottom = HomeSortDialog()
                bottom.show(this@HomeFragment.parentFragmentManager, bottom.tag)
            }
        }
    }

    private fun LayoutHomeContentsBinding.set() {

        with(itemList) {
            adapter = listAdapter
        }

        homeViewModel.beerList.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                listAdapter.updateItems(list)
            }
        })
    }
}