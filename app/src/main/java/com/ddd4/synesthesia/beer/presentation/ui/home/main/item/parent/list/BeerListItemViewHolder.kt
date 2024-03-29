package com.ddd4.synesthesia.beer.presentation.ui.home.main.item.parent.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.LayoutHomeBinding
import com.ddd4.synesthesia.beer.ext.createView
import com.ddd4.synesthesia.beer.presentation.ui.home.main.item.HomeViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.home.main.view.HomeListChildAdapter
import com.ddd4.synesthesia.beer.util.listener.EndlessRecyclerViewScrollListener

class BeerListItemViewHolder(
    itemView: View
) : HomeViewHolder<BeerListItemViewModel, LayoutHomeBinding>(itemView) {

    val adapter = HomeListChildAdapter()
    private var endlessRecyclerOnScrollListener: EndlessRecyclerViewScrollListener? = null

    companion object {
        fun newInstance(parent: ViewGroup) =
            BeerListItemViewHolder(parent.createView(R.layout.layout_home))
    }

    override fun onBind(viewModel: BeerListItemViewModel, position: Int) {
        endlessRecyclerOnScrollListener =
            object : EndlessRecyclerViewScrollListener(binding?.rvBeers?.layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.loadMore()
                }
            }

        binding?.run {
            item = viewModel
            with(rvBeers) {
                adapter = this@BeerListItemViewHolder.adapter
                endlessRecyclerOnScrollListener?.let {
                    removeOnScrollListener(it)
                    addOnScrollListener(it)
                }
            }
            executePendingBindings()
        }
        adapter.clear()
        adapter.addAll(viewModel.beers.orEmpty())
    }
}