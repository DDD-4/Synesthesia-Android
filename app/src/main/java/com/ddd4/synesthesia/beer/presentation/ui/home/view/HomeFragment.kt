package com.ddd4.synesthesia.beer.presentation.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
import com.example.hyojin.util.EndlessRecyclerViewScrollListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
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

    private val endlessRecyclerViewScrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(binding.contents.itemList.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                homeViewModel.loadMore()
            }
        }
    }

    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T?) {
                Timber.d("onItemClick ${item.toString()}")
                findNavController().navigate(HomeNavigationDirections.actionToDetail((item as Beer).id))
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

            Glide.with(this@HomeFragment)
                .asGif()
                .listener(object : RequestListener<GifDrawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        resource?.setLoopCount(1)
                        return false
                    }
                })
                .load(R.raw.home_motion)
                .into(header.ivBack)




            sort.setOnClickListener {
                val bottom = HomeSortDialog()
                bottom.show(this@HomeFragment.parentFragmentManager, bottom.tag)
            }

        }
    }

    private fun LayoutHomeContentsBinding.set() {

        with(itemList) {
            adapter = listAdapter
            addOnScrollListener(endlessRecyclerViewScrollListener)
        }

        homeViewModel.appConfig.observe(viewLifecycleOwner, Observer {
            preference.setPreference("appConfig", Gson().toJson(it))
        })

        homeViewModel.beerList.observe(viewLifecycleOwner, Observer {
            binding.emptyList.isVisible = it.isNullOrEmpty()
            listAdapter.updateItems(it ?: mutableListOf())
        })

        homeViewModel.beerFilter.observe(viewLifecycleOwner, Observer {
            binding.filterChipGroup.setFilterChips(it)
            endlessRecyclerViewScrollListener.resetState()
            homeViewModel.cursor.value = 0
            homeViewModel.load()
        })
    }

    private fun ChipGroup.setFilterChips(
        filter: BeerFilter
    ) {
        filter.run {
            if (styleFilter.isNullOrEmpty() || aromaFilter.isNullOrEmpty() || countryFilter.isNullOrEmpty()) removeAllViews()

            abvFilter?.let {
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