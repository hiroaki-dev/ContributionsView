package me.hiroaki.contributionsview

import android.graphics.Color
import android.graphics.Paint
import org.threeten.bp.DayOfWeek
import org.threeten.bp.format.TextStyle
import java.util.*

class DayOfWeekPaint(
        val fontSize: Float = 31.5f,
        private val textColor: Int = Color.GRAY,
        private val textStyle: TextStyle = TextStyle.SHORT,
        private val locale: Locale = Locale.ENGLISH,
        vararg dayOfWeeks: DayOfWeek = arrayOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
) {

    val dayOfWeeks: Map<DayOfWeek, String> = dayOfWeeks.associate { d -> Pair(d, d.getDisplayName(textStyle, locale)) }
    private val dayOfWeekPaint = Paint().apply {
        color = textColor
        textSize = fontSize
        isAntiAlias = true
    }


    fun getDayOfWeekWidth(): Float {
        val maxLengthDayText = dayOfWeeks.maxBy { s -> s.value.length }?.value ?: ""
        return dayOfWeekPaint.measureText(maxLengthDayText)
    }

    fun getPaint(): Paint = this.dayOfWeekPaint
}