package com.ddd4.synesthesia.beer.util

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.source.local.InfomationsType
import com.ddd4.synesthesia.beer.ext.toPx
import com.ddd4.synesthesia.beer.util.sort.SortType
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

@BindingAdapter("app:addChip")
fun makeChips(view: View, flavor: List<String>) {
    for (i in flavor) {
        val chip = Chip(view.context).apply {
            text = i
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            setTextColor(resources.getColor(R.color.black, null))
            chipBackgroundColor = resources.getColorStateList(R.color.white, null)

        }
        (view as ChipGroup).addView(chip)
    }
}

@BindingAdapter(value = ["forgroundSelected"])
fun forgroundSelected(view : View, type : InfomationsType) = if(type == InfomationsType.HEADER) {
    view.foreground = null
} else {
    val typeValue = TypedValue()
    view.context.theme.resolveAttribute(android.R.attr.selectableItemBackground,typeValue,true)
    view.foreground = view.context.getDrawable(typeValue.resourceId)
}

@BindingAdapter("app:sortTypeText")
fun sortTypeText(textView: TextView, type: SortType?) {
    type ?: return
    textView.text = when(type) {
        SortType.Default -> textView.resources.getString(R.string.sort_default)
        SortType.Rating -> textView.resources.getString(R.string.sort_rating)
        SortType.Review -> textView.resources.getString(R.string.sort_review)
    }

}

@BindingAdapter(value = ["space","vertical_space","horizontal_space"], requireAll = false)
fun decoration(recyclerview : RecyclerView, space : Int, verticalSpace : Int?, horizontalSpace : Int?) {
    verticalSpace?.let {
        RecyclerItemDecoration(space = space.toFloat().toPx(recyclerview.context),verticalSpace = verticalSpace.toFloat().toPx(recyclerview.context))
            .run {
                recyclerview.addItemDecoration(this)
            }
    }
    horizontalSpace?.let {
        RecyclerItemDecoration(space = space.toFloat().toPx(recyclerview.context), horizontalSpace = horizontalSpace.toFloat().toPx(recyclerview.context))
            .run {
                recyclerview.addItemDecoration(this)
            }
    }
}