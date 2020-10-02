package com.ddd4.synesthesia.beer.presentation.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
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
import com.ddd4.synesthesia.beer.util.filter.BeerFilter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val listAdapter by lazy {
        BaseItemsApdater(
            R.layout.item_home,
            BR.item,
            itemClickListener
        )
    }

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

            header.btnBeerRecommend.setOnClickListener {
                FilterDialog().run {
                    show(this@HomeFragment.parentFragmentManager, tag)
                }
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
            // TODO 추후 데이터 없는 경우에 대한 디자인 추가 되면 작업 예정
            if (it == null) {
                binding.empty.visibility = View.VISIBLE
                binding.contents.itemList.visibility = View.GONE
            } else {
                binding.empty.visibility = View.GONE
                binding.contents.itemList.visibility = View.VISIBLE
                listAdapter.updateItems(it)
            }

        })

        homeViewModel.beerFilter.observe(viewLifecycleOwner, Observer {
            binding.filterChipGroup.setFilterChips(it)
        })
    }

    private fun ChipGroup.setFilterChips(
        filter: BeerFilter
    ) {
        filter.run {
            abvFilter?.let {
                if (it.first == 0 && it.second == 10) return@let
                val value = "${it.first}% - ${it.second}%"
                makeChips(this@setFilterChips, mutableListOf(value), "abv")
            }
            styleFilter?.let { makeChips(this@setFilterChips, it, "style") }
            aromaFilter?.let { makeChips(this@setFilterChips, it, "aroma") }
            countryFilter?.let { makeChips(this@setFilterChips, it, "country") }
        }
    }

    private fun makeChips(chipGroup: ChipGroup, items: List<String>, tag: String) {
        items.asSequence().forEach { item ->
            if (chipGroup.children.asIterable().map { view -> view.tag }.contains(item)) return

            val chip: Chip = layoutInflater.inflate(
                R.layout.layout_home_filter_chip,
                binding.filterChipGroup,
                false
            ) as Chip

            chip.apply {
                text = item
                this.tag = item
                Timber.d("makeChips Item $item")
            }

            chip.setOnCloseIconClickListener {
                chipGroup.removeView(it)
                lifecycleScope.launch(Dispatchers.IO) {
                    homeViewModel.updateFilter(item, tag)
                }
            }

            chipGroup.addView(chip)
        }
    }

}