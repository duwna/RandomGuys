package com.example.randomguys.presentation.utils

import kotlin.math.roundToInt

object SliderFractionUtils {

    fun getSliderFraction(requiredRange: IntRange, actualValue: Int): Float {
        val count = requiredRange.last - requiredRange.first + 1
        return actualValue.toFloat() / count.toFloat()
    }

    fun getValueFromFraction(requiredRange: IntRange, fraction: Float): Int {
        val count = requiredRange.last - requiredRange.first + 1
        val value = count * fraction
        return if (value < 1) requiredRange.first else value.roundToInt()
    }
}
