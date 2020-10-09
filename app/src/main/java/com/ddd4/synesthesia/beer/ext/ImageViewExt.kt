package com.ddd4.synesthesia.beer.ext

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.ddd4.synesthesia.beer.R

@BindingAdapter(
    value = ["loadUrl", "tranformType", "radius", "loadingDuration", "errorDrawable"],
    requireAll = false
)
fun ImageView.loadUrl(
    url: String?,
    type: ImageTransformType? = null,
    radius: Int? = 14,
    loadingDuration: Int = 500,
    errorDrawable: Drawable? = null
) {
    Glide.with(this)
        .load(url)
        .apply {
            errorDrawable?.let {
                error(errorDrawable)
            }
            when (type) {
                ImageTransformType.ROUND -> {
                    val multiTransformation = MultiTransformation(
                        CenterCrop(),
                        FitCenter(),
                        radius?.toFloat()?.let { RoundedCorners(it.toPx(context)) }
                            ?: RoundedCorners(14f.toPx(context))
                    )
                    // 이미지 로딩 애니메이션
                    transition(DrawableTransitionOptions.withCrossFade(loadingDuration))
                    apply(RequestOptions.bitmapTransform(multiTransformation))
                }
                ImageTransformType.FIT -> {
                    override(SIZE_ORIGINAL)
                    val multiTransformation = MultiTransformation<Bitmap>(
                        CenterCrop(),
                        FitCenter()
                    )
//                        apply(RequestOptions.fitCenterTransform().centerCrop())
                    transition(DrawableTransitionOptions.withCrossFade(500))
                    apply(RequestOptions.bitmapTransform(multiTransformation))
                }
                ImageTransformType.CIRCLE -> {
                    thumbnail(0.5f)
                    apply(RequestOptions.circleCropTransform())

                }
                else -> apply(RequestOptions.fitCenterTransform().centerCrop())
            }

        }.into(this)
}

enum class ImageTransformType {
    ROUND,
    FIT,
    CIRCLE
}