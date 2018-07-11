package me.hiroaki.contributionsview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import java.util.*


class ContributionsView : View {

    private val monthPaint = MonthPaint()
    private val dayOfWeekPaint = DayOfWeekPaint()
    private val squareSize = 12
    private val squarePaint = SquarePaint()
    private val squareVerticalSpace = 4
    private val squareHorizontalSpace = 4
    private val contributions: Map<LocalDate, Int> by lazy {
        HashMap<LocalDate, Int>().apply {
            put(LocalDate.now(), 7)
            put(LocalDate.now().minusDays(8.toLong()), 1)
            put(LocalDate.now().minusDays(6.toLong()), 3)
        }
    }

    private companion object {
        val TAG = ContributionsView::class.java.simpleName
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        AndroidThreeTen.init(context)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val dpi = resources.displayMetrics.density

        Log.d(TAG, "dpi = $dpi")
        Log.d(TAG, "canvasSize: width = ${canvas.width}px, height = ${canvas.height}px")
        Log.d(TAG, "canvasSize: width = ${canvas.width / dpi}dp, height = ${canvas.height / dpi}dpi")

        Log.d(TAG, "count = ${canvas.width / ((squareSize + squareHorizontalSpace) * dpi).toInt()}")
        Log.d(TAG, "余り = ${canvas.width % ((squareSize + squareHorizontalSpace) * dpi)}")

        val offsetStart = (canvas.width - dayOfWeekPaint.getDayOfWeekWidth()) % ((squareSize + squareHorizontalSpace) * dpi) / 2
        drawDayOfWeek(canvas, offsetStart, monthPaint.getMonthHeight())
        drawContributions(canvas, offsetStart + dayOfWeekPaint.getDayOfWeekWidth(), monthPaint.getMonthHeight())

    }

    private fun drawDayOfWeek(canvas: Canvas, offsetStart: Float = 0f, offsetTop: Float = 0f) {
        val dpi = resources.displayMetrics.density

        dayOfWeekPaint.dayOfWeeks.forEach { d ->
            val tmp = d.key.value + 1
            val n = (if (tmp > 7) 1 else tmp) - 1
            canvas.drawText(
                    d.value,
                    offsetStart,
                    offsetTop + n * squareSize * dpi + n * squareVerticalSpace * dpi + dayOfWeekPaint.fontSize,
                    dayOfWeekPaint.getPaint()
            )
        }
    }

    private fun drawContributions(canvas: Canvas, offsetStart: Float = 0f, offsetTop: Float = 0f) {
        val dpi = resources.displayMetrics.density

        val weeks = ((canvas.width - offsetStart) / ((squareSize + squareHorizontalSpace) * dpi)).toInt()
        var x1 = offsetStart
        var x2 = x1 + squareSize * dpi

        val tmp = LocalDate.now().dayOfWeek.value + 1
        val todayDayOfWeek = if (tmp > 7) 1 else tmp

        // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
        for (week in 1..weeks) {
            var y1 = offsetTop
            var y2 = y1 + squareSize * dpi
            for (n in 1..7) {
                if (week == weeks && n > todayDayOfWeek) break
                val commitDate = LocalDate.now().minusDays(((weeks - week) * 7 - (todayDayOfWeek - n)).toLong())

                // 月の描画
                if (commitDate.dayOfMonth == 1) drawMonth(canvas, commitDate.month, x1)

                // 四角の描画
                canvas.drawRect(
                        x1,
                        y1,
                        x2,
                        y2,
                        squarePaint.getPaint(contributions[commitDate] ?: 0)
                )

                y1 = y2 + squareVerticalSpace * dpi
                y2 = y1 + squareSize * dpi
            }
            x1 = x2 + squareHorizontalSpace * dpi
            x2 = x1 + squareSize * dpi
        }
    }

    private fun drawMonth(canvas: Canvas, month: Month, offsetStart: Float = 0f, offsetTop: Float = 0f) {
        val dpi = resources.displayMetrics.density
        val monthInfo = monthPaint.getMonth(month)

        canvas.drawText(
                monthInfo.first,
                offsetStart + squareSize * dpi / 2 - monthInfo.second / 2,
                monthPaint.getMonthHeight(),
                monthPaint.getPaint()
        )
    }
}
