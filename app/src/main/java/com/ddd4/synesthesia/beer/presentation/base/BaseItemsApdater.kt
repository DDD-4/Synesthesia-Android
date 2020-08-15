package com.ddd4.synesthesia.beer.presentation.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.util.ItemClickListener
import com.ddd4.synesthesia.beer.util.ItemLongClickListener

class BaseItemsApdater(
    private val layoutId: Int,
    private val bindingVariableId: Int? = -1,
    private val clickItemEvent: ItemClickListener? = null,
    private val longClickItemEvent: ItemLongClickListener? = null
) : BaseRecyclerView.Adapter<Any, ViewDataBinding>(
    layoutId = layoutId,
    bindingVariableId = bindingVariableId,
    clickItemEvent = clickItemEvent,
    longClickItemEvent = longClickItemEvent
) {

    private val items = mutableListOf<Any>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerView.ViewHolder<ViewDataBinding> {
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerView.ViewHolder<ViewDataBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
    }

    fun addItems(items : List<Any>) {

    }
}