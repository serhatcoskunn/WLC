package com.ahe.wlc.CustomViews.Slider

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager

/**
 * Created by jameson on 12/19/15.
 */
object ScreenUtil {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    open fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = Point()
        wm.defaultDisplay.getSize(p)
        return p.x
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    open fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = Point()
        wm.defaultDisplay.getSize(p)
        return p.y
    }

    open fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    open fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}
