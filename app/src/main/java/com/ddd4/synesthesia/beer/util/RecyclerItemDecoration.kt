package com.ddd4.synesthesia.beer.util

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ddd4.synesthesia.beer.ext.toPx


class RecyclerItemDecoration(
    private var space : Int,
    private var horizontalSpace : Int? = null,
    private var verticalSpace : Int? = null
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position: Int = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        var spanIndex = 0
        var spanCount = 0
        when(parent.layoutManager) {
            is GridLayoutManager -> {
                spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
                spanCount = (parent.layoutManager as GridLayoutManager).spanCount
                gridOffset(outRect,spanIndex,spanCount)
            }
            is LinearLayoutManager -> {
                when((parent.layoutManager as LinearLayoutManager).orientation) {
                    HORIZONTAL -> {
                        horizontalOffset(outRect,position,itemCount)
                    }
                    VERTICAL -> {
                        verticalOffset(outRect,position,itemCount)
                    }
                }
            }
            else -> {
                spanIndex = 0
            }
        }

    }

    private fun gridOffset(outRect: Rect, spanIndex : Int, spanCount : Int) {
        val spaceHalf = space / 2
        if (spanIndex == 0) {
            //가로 첫번째 아이템
            outRect.left = space
            outRect.right = spaceHalf
            outRect.top = space
            outRect.bottom = spaceHalf
        } else if(spanCount > 0 && (spanIndex % spanCount) == spanCount -1){
            //가로 마지막 아이템
            outRect.left = spaceHalf
            outRect.right = space
            outRect.top = spaceHalf
            outRect.bottom = space
        } else {
            //가로 중간 아이템
            outRect.left = spaceHalf
            outRect.right = spaceHalf
            outRect.top = spaceHalf
            outRect.bottom = spaceHalf
        }
    }

    private fun horizontalOffset(outRect: Rect, position : Int, itemCount : Int) {
        val spaceHalf = space / 2
        if (position == 0) {
            //첫번째 아이템
            outRect.left = horizontalSpace ?: space
            outRect.right = spaceHalf
            outRect.top = space
            outRect.bottom = space
        } else if(position == itemCount-1){
            // 마지막 아이템
            outRect.left = spaceHalf
            outRect.right = horizontalSpace ?: space
            outRect.top = space
            outRect.bottom = space
        } else {
            // 중간 아이템
            outRect.left = spaceHalf
            outRect.right = spaceHalf
            outRect.top = space
            outRect.bottom = space
        }
    }
    private fun verticalOffset(outRect: Rect, position : Int, itemCount : Int) {
        val spaceHalf = space / 2
        if (position == 0) {
            //첫번째 아이템
            outRect.left = space
            outRect.right = space
            outRect.top = verticalSpace ?: space
            outRect.bottom = spaceHalf
        } else if(position == itemCount-1){
            // 마지막 아이템
            outRect.left = space
            outRect.right = space
            outRect.top = spaceHalf
            outRect.bottom = verticalSpace ?: space
        } else {
            // 중간 아이템
            outRect.left = space
            outRect.right = space
            outRect.top = spaceHalf
            outRect.bottom = spaceHalf
        }
    }
    private fun linearOffset(outRect: Rect, position : Int, itemCount : Int) {
        val spaceHalf = space / 2
        if (position == 0) {
            //첫번째 아이템
            outRect.left = space
            outRect.right = space
            outRect.top = verticalSpace ?: space
            outRect.bottom = spaceHalf
        } else if(position == itemCount-1){
            // 마지막 아이템
            outRect.left = space
            outRect.right = horizontalSpace ?: space
            outRect.top = spaceHalf
            outRect.bottom = verticalSpace ?: space
        } else {
            // 중간 아이템
            outRect.left = space
            outRect.right = space
            outRect.top = spaceHalf
            outRect.bottom = spaceHalf
        }
    }

    // dp -> pixel 단위로 변경
    private fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }
}