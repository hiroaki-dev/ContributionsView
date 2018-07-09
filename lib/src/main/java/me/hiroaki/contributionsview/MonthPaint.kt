package me.hiroaki.contributionsview

import android.graphics.Color
import android.graphics.Paint
import org.threeten.bp.Month
import org.threeten.bp.format.TextStyle
import java.util.*

class MonthPaint(
        val fontSize: Float = 31.5f,
        private val textColor: Int = Color.GRAY,
        private val textStyle: TextStyle = TextStyle.SHORT,
        private val locale: Locale = Locale.ENGLISH
) {

    private val dayOfWeekPaint = Paint().apply {
        color = textColor
        textSize = fontSize
        isAntiAlias = true
    }


    fun getMonthHeight(): Float {
        return fontSize
    }

    fun getMonth(month: Month): Pair<String, Float> {
        val monthText = month.getDisplayName(textStyle, locale)
        return Pair(monthText, dayOfWeekPaint.measureText(monthText))
    }

    fun getPaint(): Paint = this.dayOfWeekPaint
}