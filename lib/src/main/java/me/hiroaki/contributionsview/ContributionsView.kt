package me.hiroaki.contributionsview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDate
import java.util.*


class ContributionsView : View {

    private val squareSize = 12
    private val squarePaint = SquarePaint()
    private val squareVerticalPadding = 4
    private val squareHorizontalPadding = 4
    private val contributions: Map<LocalDate, Int> by lazy {
        HashMap<LocalDate, Int>().apply {
            put(LocalDate.now(), 7)
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

        Log.d(TAG, "count = ${canvas.width / ((squareSize + squareHorizontalPadding) * dpi).toInt()}")
        Log.d(TAG, "余り = ${canvas.width % ((squareSize + squareHorizontalPadding) * dpi)}")

        // TODO: 曜日出力できるようになったらその横幅を考慮
        val offsetStart = canvas.width % ((squareSize + squareHorizontalPadding) * dpi) / 2
        drawContributions(canvas, offsetStart)
    }

    private fun drawContributions(canvas: Canvas, offsetStart: Float = 0f, offsetTop: Float = 0f) {
        val dpi = resources.displayMetrics.density

        val weeks = ((canvas.width - offsetStart) / ((squareSize + squareHorizontalPadding) * dpi)).toInt()
        var x1 = offsetStart
        var x2 = x1 + squareSize * dpi

        val tmp = LocalDate.now().dayOfWeek.value + 1
        val todayDayOfWeek = if (tmp > 7) 1 else tmp

        // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
        for (week in 1..weeks) {
            var y1 = offsetTop
            var y2 = y1 + squareSize * dpi
            for (dayOfWeek in 1..7) {
                if (week == weeks && dayOfWeek > todayDayOfWeek) break
                val commitDate = LocalDate.now().minusDays(((weeks - week) * 7 + (dayOfWeek - 1)).toLong())
                canvas.drawRect(
                        x1,
                        y1,
                        x2,
                        y2,
                        squarePaint.getPaint(contributions[commitDate] ?: 0)
                )
                y1 = y2 + squareVerticalPadding * dpi
                y2 = y1 + squareSize * dpi
            }
            x1 = x2 + squareHorizontalPadding * dpi
            x2 = x1 + squareSize * dpi
        }
    }
}
