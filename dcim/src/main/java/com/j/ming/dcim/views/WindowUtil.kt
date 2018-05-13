package com.j.ming.dcim.views

import android.animation.ValueAnimator
import android.app.Activity
import android.view.Window
import android.view.WindowManager

/**
 * Created by Sunny on 2017/8/26 0026.
 */

object WindowUtil {
    fun changeWindowAlpha(activity: Activity, open: Boolean) {
        if (open)
            changeWindowAlpha(activity, 1f, 0.7f)
        else
            changeWindowAlpha(activity, 0.7f, 1f)
    }

    private fun changeWindowAlpha(activity: Activity, startAlpha: Float, endAlpha: Float) {
        val valueAnimator = ValueAnimator.ofFloat(startAlpha, endAlpha)
                .setDuration(300)
        valueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            WindowUtil.changeWindowAlpha(activity, value)
        }
        valueAnimator.start()
    }

    private fun changeWindowAlpha(activity: Activity?, alpha: Float) {
        if (activity == null || alpha < 0 || alpha > 1.0f)
            return
        val window = activity.window ?: return
        val lp = window.attributes
        lp.alpha = alpha
        window.attributes = lp
    }
}
