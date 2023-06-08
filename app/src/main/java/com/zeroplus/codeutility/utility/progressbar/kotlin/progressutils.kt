package com.zeroplus.codeutility.utility.progressbar.kotlin

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.zeroplus.codeutility.utility.progressbar.java.progressutils

fun dp2px(resources: Resources, dp: Float): Float {
    val scale = resources.displayMetrics.density
    return dp * scale + 0.5f
}

fun sp2px(resources: Resources, sp: Float): Float {
    val scale = resources.displayMetrics.scaledDensity
    return sp * scale
}

fun getBitmap(context: Context?, drawableId: Int): Bitmap? {
    val drawable = AppCompatResources.getDrawable(context!!, drawableId)
    return progressutils.getBitmap(drawable)
}

fun getBitmap(drawable: Drawable): Bitmap? {
    return if (drawable is BitmapDrawable) {
        drawable.bitmap
    } else if (drawable is VectorDrawableCompat || drawable is VectorDrawable) {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        bitmap
    } else {
        throw IllegalArgumentException("unsupported drawable type")
    }
}