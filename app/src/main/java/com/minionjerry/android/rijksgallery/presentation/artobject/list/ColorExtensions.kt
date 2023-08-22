package com.minionjerry.android.rijksgallery.presentation.artobject.list

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

fun @receiver:ColorInt Int.getContrastColor(): Int {
    val whiteContrast = ColorUtils.calculateContrast(Color.WHITE, this)
    val blackContrast = ColorUtils.calculateContrast(Color.BLACK, this)
    return if (whiteContrast > blackContrast) Color.WHITE else Color.BLACK
}