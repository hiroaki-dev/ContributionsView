package me.hiroaki.contributionsview

import android.graphics.Paint

class LegendPaint(
        private val fontSize: Float,
        private val textColor: Int,
        val lessText: String,
        val moreText: String
) {

    private val legendPaint = Paint().apply {
        color = textColor
        textSize = fontSize
        isAntiAlias = true
    }


    fun getTextHeight(): Float = legendPaint.fontMetrics.leading - legendPaint.fontMetrics.ascent

    fun getHeight(): Float = legendPaint.fontMetrics.bottom - legendPaint.fontMetrics.top

    fun getFontTopSpace(): Float = legendPaint.fontMetrics.ascent - legendPaint.fontMetrics.top

    fun getLessWidth(): Float = legendPaint.measureText(lessText)

    fun getMoreWidth(): Float = legendPaint.measureText(moreText)

    fun getPaint(): Paint = this.legendPaint
}