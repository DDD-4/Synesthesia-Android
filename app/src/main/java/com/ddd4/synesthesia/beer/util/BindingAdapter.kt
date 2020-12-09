package com.ddd4.synesthesia.beer.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.source.local.InfomationsType
import com.ddd4.synesthesia.beer.ext.dp
import com.ddd4.synesthesia.beer.ext.toPx
import com.ddd4.synesthesia.beer.util.filter.BeerFilter
import com.ddd4.synesthesia.beer.util.sort.SortType
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup


@BindingAdapter("app:addChip")
fun makeChips(chipGroup: ChipGroup, flavor: List<String>?) {
    chipGroup.removeAllViews()
    flavor?.asSequence()?.forEach {
        val chip = Chip(chipGroup.context).apply {
            setChipDrawable(ChipDrawable.createFromResource(this.context, R.xml.chip_home_item))
            setTextColor(resources.getColor(R.color.black, null))
            ensureAccessibleTouchTarget(20.dp)
            textSize = 12f
            typeface = ResourcesCompat.getFont(chipGroup.context, R.font.notosans_kr_bold)
            text = it
        }
        chipGroup.addView(chip)
    }
}

@BindingAdapter(value = ["forgroundSelected"])
fun forgroundSelected(view: View, type: InfomationsType) = if (type == InfomationsType.HEADER) {
    view.foreground = null
} else {
    val typeValue = TypedValue()
    view.context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typeValue, true)
    view.foreground = view.context.getDrawable(typeValue.resourceId)
}

@BindingAdapter("app:sortTypeText")
fun sortTypeText(textView: TextView, type: SortType?) {
    type ?: return
    textView.text = when (type) {
        SortType.Default -> textView.resources.getString(R.string.sort_default)
        SortType.Rating -> textView.resources.getString(R.string.sort_rating)
        SortType.Review -> textView.resources.getString(R.string.sort_review)
    }

}

@BindingAdapter(value = ["space", "vertical_space", "horizontal_space"], requireAll = false)
fun decoration(
    recyclerview: RecyclerView,
    space: Int?,
    verticalSpace: Int?,
    horizontalSpace: Int?
) {
    verticalSpace?.let {
        RecyclerItemDecoration(
            space = space?.toFloat()?.toPx(recyclerview.context) ?: 0,
            verticalSpace = verticalSpace.toFloat().toPx(
                recyclerview.context
            )
        )
            .run {
                recyclerview.addItemDecoration(this)
            }
    }
    horizontalSpace?.let {
        RecyclerItemDecoration(
            space = space?.toFloat()?.toPx(recyclerview.context) ?: 0,
            horizontalSpace = horizontalSpace.toFloat().toPx(
                recyclerview.context
            )
        )
            .run {
                recyclerview.addItemDecoration(this)
            }
    }
}

@BindingAdapter("app:updateCountText")
fun updateCountText(countView: TextView, selectedItemList: MutableLiveDataList<String>) {
    if (selectedItemList.isNotEmpty()) {
        val context = countView.context

        val prefix = context.getString(R.string.update_count_text_prefix, selectedItemList[0])
        val suffix =
            context.getString(R.string.update_count_text_suffix, selectedItemList.count().minus(1))

        val typeface =
            ResourcesCompat.getFont(context, R.font.notosans_kr_bold) ?: Typeface.DEFAULT_BOLD

        val span = SpannableString(suffix).apply {
            setSpan(
                CustomTypefaceSpan(
                    typeface,
                    context.resources.getColor(R.color.butterscotch, null)
                ), 0, suffix.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        val text = SpannableStringBuilder(prefix).append(" ").append(span)

        countView.text =
            if (selectedItemList.count() > 1) text else selectedItemList[0]
    } else {
        countView.text = ""
    }
}

@BindingAdapter("app:updateAbvRange")
fun updateAbvRange(abvTextView: TextView, abvRange: Pair<Int, Int>?) {
    if (abvRange == null) {
        abvTextView.text = ""
        return
    }
    val minAbv = "${abvRange.first}%"
    val maxAbv = "${abvRange.second}%"

    val span = SpannableStringBuilder(minAbv).append(" ")

    val imageSpan = object : ImageSpan(abvTextView.context, R.drawable.layer_filter_line) {
        override fun draw(
            canvas: Canvas,
            text: CharSequence?,
            start: Int,
            end: Int,
            x: Float,
            top: Int,
            y: Int,
            bottom: Int,
            paint: Paint
        ) {
            val drawable = drawable
            canvas.save()
            // Align Center
            val transY = bottom.minus(top).div(2).minus(drawable.bounds.height().div(2))

            canvas.translate(x, transY.toFloat())
            drawable.draw(canvas)
            canvas.restore()
        }
    }

    span.setSpan(imageSpan, span.length - 1, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    span.append(maxAbv)
    abvTextView.setText(span, TextView.BufferType.SPANNABLE)
}

@BindingAdapter("app:filterVisibility")
fun homeFilterVisibility(view: View, filter: BeerFilter?) {
    filter ?: return

    with(filter) {
        view.visibility =
            if (styleFilter != null || aromaFilter != null || abvFilter != null || countryFilter != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

}

@BindingAdapter(
    value = ["layout_margin_top", "layout_margin_bottom", "layout_margin_start", "layout_margin_end"],
    requireAll = false
)
fun margin(
    view: ConstraintLayout,
    marginTop: Int?,
    marginBottom: Int?,
    marginStart: Int?,
    marginEnd: Int?
) {
    val params = ConstraintLayout.LayoutParams(view.layoutParams)
    params.setMargins(
        marginStart?.dp ?: 0,
        marginTop?.dp ?: 0,
        marginEnd?.dp ?: 0,
        marginBottom?.dp ?: 0
    )

//    val px = TypedValue.applyDimension(
//        TypedValue.COMPLEX_UNIT_DIP,
//        view,
//        view.resources.displayMetrics
//    ).toInt()

    view.layoutParams = params
}

@BindingAdapter(value = ["layoutScrolFlagMode"])
fun setLayoutScrollFlagMode(view: CollapsingToolbarLayout, isEmpty : Boolean) {
    val params = view.layoutParams as? AppBarLayout.LayoutParams
    val appBarLayoutParams = view.layoutParams
    if (isEmpty) {
        params?.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
        view.layoutParams = appBarLayoutParams
    } else {
        params?.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
        view.layoutParams = appBarLayoutParams
    }
}