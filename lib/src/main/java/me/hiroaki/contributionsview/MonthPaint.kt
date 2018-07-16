package me.hiroaki.contributionsview

import android.graphics.Paint
import org.threeten.bp.Month
import org.threeten.bp.format.TextStyle
import java.util.*

class MonthPaint(
        private val fontSize: Float,
        private val textColor: Int,
        private val textStyle: TextStyle = TextStyle.SHORT,
        private val locale: Locale = Locale.ENGLISH
) {

    private val monthPaint = Paint().apply {
        color = textColor
        textSize = fontSize
        isAntiAlias = true
    }


    fun getTextHeight(): Float = fontSize

    fun getMonth(month: Month): Pair<String, Float> {
        val monthText = month.getDisplayName(textStyle, locale)
        return Pair(monthText, monthPaint.measureText(monthText))
    }

    fun getPaint(): Paint = this.monthPaint
}