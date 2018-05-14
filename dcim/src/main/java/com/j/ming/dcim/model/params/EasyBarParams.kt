package com.j.ming.dcim.model.params

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.j.ming.dcim.R

class EasyBarParams(val title: String = "", @DrawableRes val leftRes: Int = R.drawable.back,
                    @DrawableRes val rightRes: Int = R.drawable.icon_sure, val leftText: String = "",
                    val rightText: String = "", @StringRes val titleRes: Int = -1,
                    @ColorRes val barBgColor: Int = R.color.colorPrimary){
}