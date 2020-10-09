package com.ddd4.synesthesia.beer.util.chip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.ext.dp
import kotlinx.android.synthetic.main.layout_style_chip.view.*

class StyleChipLayout : FrameLayout {

    constructor(context: Context) : super(context) {
        initView()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initView()
    }

    private fun initView() {
        val view = LayoutInflater.from(this.context).inflate(R.layout.layout_style_chip, this, false)
        addView(view)
    }

    fun setText(text: String) {
        tv_style.text = text
    }
}