package me.hiroaki.contributionsview

import android.graphics.Paint
import org.threeten.bp.DayOfWeek
import org.threeten.bp.format.TextStyle
import java.util.*

class DayOfWeekPaint(
        val fontSize: Float,
        private val textColor: Int,
        private val textStyle: TextStyle = TextStyle.SHORT,
        private val locale: Locale = Locale.ENGLISH,
        vararg dayOfWeeks: DayOfWeek
) {

    val dayOfWeeks: Map<DayOfWeek, String> = dayOfWeeks.associate { d -> Pair(d, d.getDisplayName(textStyle, locale)) }
    private val dayOfWeekPaint = Paint().apply {
        color = textColor
        textSize = fontSize
        isAntiAlias = true
    }

    fun getTextHeight(): Float = fontSize

    fun getDayOfWeekWidth(): Float = dayOfWeeks.map { d -> dayOfWeekPaint.measureText(d.value) }.max() ?: 0f

    fun getPaint(): Paint = this.dayOfWeekPaint
}