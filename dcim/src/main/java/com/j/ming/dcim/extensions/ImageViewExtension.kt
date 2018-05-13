package com.j.ming.dcim.extensions

import android.graphics.drawable.ColorDrawable
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.j.ming.dcim.GlideApp

/**
 * Created by sunny on 18-1-2.
 */

fun ImageView.loadCircle(url: String) {
    GlideApp.with(this)
            .load(url)
            .into(this)
}

fun ImageView.loadCircle(@DrawableRes res: Int) {
    GlideApp.with(this)
            .load(res)
            .into(this)
}

fun ImageView.load(url: String) {
    GlideApp.with(this)
            .load(url)
            .centerCrop()
            .error(ColorDrawable(0x00ffffff))
            .placeholder(ColorDrawable(0x00ffffff))
            .into(this)
}

fun ImageView.load(@DrawableRes res: Int) {
    GlideApp.with(this)
            .load(res)
            .into(this)
}
