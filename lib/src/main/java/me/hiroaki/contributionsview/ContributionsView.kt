package me.hiroaki.contributionsview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import java.util.*


class ContributionsView : View {

    private val monthPaint: MonthPaint
    private val dayOfWeekPaint: DayOfWeekPaint
    private val squareSize: Float
    private val squarePaint: SquarePaint
    private val squareVerticalSpace: Float
    private val squareHorizontalSpace: Float
    private val contributionsLeftSpace: Float
    private val contributionsTopSpace: Float
    private var contributions: HashMap<LocalDate, Int> = HashMap()

    private companion object {
        val TAG = ContributionsView::class.java.simpleName
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        AndroidThreeTen.init(context)
        val xmlAttributes = context.obtainStyledAttributes(attrs, R.styleable.ContributionsView)
        squareSize = xmlAttributes.getDimension(R.styleable.ContributionsView_square_size, 32f)
        squareVerticalSpace = xmlAttributes.getDimension(R.styleable.ContributionsView_square_vertical_space, 9f)
        squareHorizontalSpace = xmlAttributes.getDimension(R.styleable.ContributionsView_square_vertical_space, 9f)
        squarePaint = SquarePaint(
                xmlAttributes.getColor(
                        R.styleable.ContributionsView_square_color_of_no_commit,
                        Color.argb(255, 238, 238, 238)
                ),
                xmlAttributes.getColor(
                        R.styleable.ContributionsView_square_color_of_one_commit,
                        Color.argb(255, 204, 226, 149)
                ),
                xmlAttributes.getColor(
                        R.styleable.ContributionsView_square_color_of_two_commit,
                        Color.argb(255, 140, 198, 120)
                ),
                xmlAttributes.getColor(
                        R.styleable.ContributionsView_square_color_of_three_commit,
                        Color.argb(255, 73, 150, 71)
                ),
                xmlAttributes.getColor(
                        R.styleable.ContributionsView_square_color_of_over_four_commit,
                        Color.argb(255, 47, 94, 46)
                )
        )

        contributionsLeftSpace = xmlAttributes.getDimension(R.styleable.ContributionsView_contributions_left_space, 9f)
        contributionsTopSpace = xmlAttributes.getDimension(R.styleable.ContributionsView_contributions_top_space, 15f)

        dayOfWeekPaint = DayOfWeekPaint(
                xmlAttributes.getDimension(R.styleable.ContributionsView_day_of_week_font_size, 31.5f),
                xmlAttributes.getColor(R.styleable.ContributionsView_day_of_week_font_color, Color.GRAY)
        )

        monthPaint = MonthPaint(
                xmlAttributes.getDimension(R.styleable.ContributionsView_month_font_size, 31.5f),
                xmlAttributes.getColor(R.styleable.ContributionsView_month_font_color, Color.GRAY)
        )
        xmlAttributes.recycle()
    }

    fun setContributionsMap(contributions: HashMap<LocalDate, Int>) {
        this.contributions = contributions
        invalidate()
    }

    fun setCommit(date: LocalDate, amount: Int) {
        contributions[date] = amount
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val dpi = resources.displayMetrics.density

        val minWidth = paddingStart + paddingEnd + (120 * dpi).toInt()
        val minHeight = paddingTop + paddingBottom + (100 * dpi).toInt()

        setMeasuredDimension(View.resolveSize(minWidth, widthMeasureSpec), View.resolveSize(minHeight, heightMeasureSpec))
    }

    override fun onDraw(canvas: Canvas) {
        val offsetStart = (canvas.width - dayOfWeekPaint.getDayOfWeekWidth() - paddingStart - paddingEnd - contributionsLeftSpace) % (squareSize + squareHorizontalSpace) / 2

        drawDayOfWeek(
                canvas,
                paddingStart.toFloat(),
                paddingTop + monthPaint.getMonthHeight()
        )

        drawContributions(
                canvas,
                paddingStart + offsetStart + dayOfWeekPaint.getDayOfWeekWidth() + contributionsLeftSpace,
                paddingTop + monthPaint.getMonthHeight() + contributionsTopSpace,
                paddingEnd.toFloat()
        )
    }

    private fun drawDayOfWeek(canvas: Canvas, spaceLeft: Float = 0f, spaceTop: Float = 0f) {
        dayOfWeekPaint.dayOfWeeks.forEach { d ->
            val tmp = d.key.value + 1
            val n = (if (tmp > 7) 1 else tmp) - 1
            dayOfWeekPaint.getDayOfWeekWidth()
            canvas.drawText(
                    d.value,
                    spaceLeft,
                    spaceTop + n * squareSize + n * squareVerticalSpace + dayOfWeekPaint.fontSize,
                    dayOfWeekPaint.getPaint()
            )
        }
    }

    private fun drawContributions(canvas: Canvas, spaceLeft: Float = 0f, spaceTop: Float = 0f, spaceRight: Float = 0f) {
        val weeks = ((canvas.width - spaceLeft - spaceRight) / (squareSize + squareHorizontalSpace)).toInt()
        var x1 = spaceLeft
        var x2 = x1 + squareSize

        val tmp = LocalDate.now().dayOfWeek.value + 1
        val todayDayOfWeek = if (tmp > 7) 1 else tmp

        // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
        for (week in 1..weeks) {
            var y1 = spaceTop
            var y2 = y1 + squareSize
            for (n in 7 downTo 1) {
                if (week == weeks && n > todayDayOfWeek) break
                val commitDate = LocalDate.now().minusDays(((weeks - week) * 7 + n - 1).toLong())

                // 月の描画
                if (commitDate.dayOfMonth == 1) drawMonth(canvas, commitDate.month, x1, paddingTop.toFloat())

                // 四角の描画
                canvas.drawRect(
                        x1,
                        y1,
                        x2,
                        y2,
                        squarePaint.getPaint(contributions[commitDate] ?: 0)
                )

                y1 = y2 + squareVerticalSpace
                y2 = y1 + squareSize
            }
            x1 = x2 + squareHorizontalSpace
            x2 = x1 + squareSize
        }
    }

    private fun drawMonth(canvas: Canvas, month: Month, spaceLeft: Float = 0f, spaceTop: Float = 0f) {
        val monthInfo = monthPaint.getMonth(month)

        canvas.drawText(
                monthInfo.first,
                spaceLeft + squareSize / 2 - monthInfo.second / 2,
                spaceTop + monthPaint.getMonthHeight(),
                monthPaint.getPaint()
        )
    }
}
