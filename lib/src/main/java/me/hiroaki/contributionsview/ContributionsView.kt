package me.hiroaki.contributionsview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import java.util.*
import kotlin.collections.HashMap


class ContributionsView : View {

    private val monthPaint: MonthPaint
    private val isDisplayMonth: Boolean
    private val dayOfWeekPaint: DayOfWeekPaint
    private val isDisplayDayOfWeek: Boolean
    private val legendPaint: LegendPaint
    private val legendTextSpace: Float
    private val legendSquareSpace: Float
    private val legendSquareSize: Float
    private val legendTopSpace: Float
    private val isDisplayLegend: Boolean
    private val squareSize: Float
    private val squarePaint: SquarePaint
    private val squareVerticalSpace: Float
    private val squareHorizontalSpace: Float
    private val contributionsLeftSpace: Float
    private val contributionsTopSpace: Float
    private var contributions: HashMap<LocalDate, Int> = HashMap()
    var isMondayStart: Boolean
        set(value) {
            field = value
            invalidate()
        }

    var startLocalDate: LocalDate private set
    var endLocalDate: LocalDate
        set(value) {
            field = value
            invalidate()
        }

    private companion object {
        val TAG = ContributionsView::class.java.simpleName
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        AndroidThreeTen.init(context)
        startLocalDate = LocalDate.now()
        endLocalDate = LocalDate.now()
        val dpi = resources.displayMetrics.density
        val xmlAttributes = context.obtainStyledAttributes(attrs, R.styleable.ContributionsView)
        squareSize = xmlAttributes.getDimension(R.styleable.ContributionsView_square_size, 15 * dpi)
        squareVerticalSpace = xmlAttributes.getDimension(R.styleable.ContributionsView_square_vertical_space, 4 * dpi)
        squareHorizontalSpace = xmlAttributes.getDimension(R.styleable.ContributionsView_square_vertical_space, 4 * dpi)
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

        contributionsLeftSpace = xmlAttributes.getDimension(R.styleable.ContributionsView_contributions_left_space, 4 * dpi)
        contributionsTopSpace = xmlAttributes.getDimension(R.styleable.ContributionsView_contributions_top_space, 7 * dpi)

        isMondayStart = xmlAttributes.getBoolean(R.styleable.ContributionsView_is_monday_start, true)
        val defaultDayOfWeeks = if (isMondayStart) {
            arrayOf(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY)
        } else {
            arrayOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        }
        dayOfWeekPaint = DayOfWeekPaint(
                xmlAttributes.getDimension(R.styleable.ContributionsView_day_of_week_font_size, 15 * dpi),
                xmlAttributes.getColor(R.styleable.ContributionsView_day_of_week_font_color, Color.GRAY),
                dayOfWeeks = *defaultDayOfWeeks
        )
        isDisplayDayOfWeek = xmlAttributes.getBoolean(R.styleable.ContributionsView_is_display_day_of_week, true)


        monthPaint = MonthPaint(
                xmlAttributes.getDimension(R.styleable.ContributionsView_month_font_size, 15 * dpi),
                xmlAttributes.getColor(R.styleable.ContributionsView_month_font_color, Color.GRAY)
        )
        isDisplayMonth = xmlAttributes.getBoolean(R.styleable.ContributionsView_is_display_month, true)

        legendPaint = LegendPaint(
                xmlAttributes.getDimension(R.styleable.ContributionsView_legend_font_size, 15 * dpi),
                Color.GRAY,
                "Less",
                "More"
        )
        legendTextSpace = xmlAttributes.getDimension(R.styleable.ContributionsView_legend_text_space, 7 * dpi)
        legendSquareSpace = xmlAttributes.getDimension(R.styleable.ContributionsView_legend_square_space, 4 * dpi)
        legendSquareSize = xmlAttributes.getDimension(R.styleable.ContributionsView_legend_square_size, 15 * dpi)
        legendTopSpace = xmlAttributes.getDimension(R.styleable.ContributionsView_legend_top_space, 7 * dpi)
        isDisplayLegend = xmlAttributes.getBoolean(R.styleable.ContributionsView_is_display_legend, true)

        xmlAttributes.recycle()
    }

    fun setContributionsMap(contributions: HashMap<LocalDate, Int>) {
        this.contributions = contributions
        invalidate()
    }

    fun setContributionsDateMap(contributions: HashMap<Date, Int>) {
        this.contributions = contributions.mapKeysTo(HashMap()) {
            DateTimeUtils.toZonedDateTime(Calendar.getInstance().apply { time = it.key }).toLocalDate()
        }
        invalidate()
    }

    fun setContributionsCalendarMap(contributions: HashMap<Calendar, Int>) {
        this.contributions = contributions.mapKeysTo(HashMap()) {
            DateTimeUtils.toZonedDateTime(it.key).toLocalDate()
        }
        invalidate()
    }

    fun setCommit(localDate: LocalDate, amount: Int) {
        contributions[localDate] = amount
        invalidate()
    }

    fun setCommit(date: Date, amount: Int) {
        val localDate = DateTimeUtils.toZonedDateTime(Calendar.getInstance().apply { time = date }).toLocalDate()
        contributions[localDate] = amount
        invalidate()
    }

    fun setCommit(calendar: Calendar, amount: Int) {
        val localDate = DateTimeUtils.toZonedDateTime(calendar).toLocalDate()
        contributions[localDate] = amount
        invalidate()
    }

    fun addCommit(localDate: LocalDate, amount: Int) {
        val newAmount = (contributions[localDate] ?: 0) + amount
        if (newAmount < 0) throw InvalidAmountException(newAmount)
        contributions[localDate] = newAmount
        invalidate()
    }

    fun addCommit(date: Date, amount: Int) {
        val localDate = DateTimeUtils.toZonedDateTime(Calendar.getInstance().apply { time = date }).toLocalDate()
        val newAmount = (contributions[localDate] ?: 0) + amount
        if (newAmount < 0) throw InvalidAmountException(newAmount)
        contributions[localDate] = newAmount
        invalidate()
    }

    fun addCommit(calendar: Calendar, amount: Int) {
        val localDate = DateTimeUtils.toZonedDateTime(calendar).toLocalDate()
        val newAmount = (contributions[localDate] ?: 0) + amount
        if (newAmount < 0) throw InvalidAmountException(newAmount)
        contributions[localDate] = newAmount
        invalidate()
    }

    fun getStartDate(): Date = DateTimeUtils.toSqlDate(startLocalDate)

    fun getStartCalendar(): Calendar = Calendar.getInstance().apply {
        time = DateTimeUtils.toSqlDate(startLocalDate)
    }

    fun getEndDate(): Date = DateTimeUtils.toSqlDate(endLocalDate)

    fun getEndCalendar(): Calendar = Calendar.getInstance().apply {
        time = DateTimeUtils.toSqlDate(endLocalDate)
    }

    fun setEndDate(date: Date) {
        endLocalDate = DateTimeUtils.toZonedDateTime(Calendar.getInstance().apply { time = date }).toLocalDate()
    }

    fun setEndCalendar(calendar: Calendar) {
        endLocalDate = DateTimeUtils.toZonedDateTime(calendar).toLocalDate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth = width
        val minHeight = paddingTop + if (isDisplayMonth) { monthPaint.getTextHeight() + contributionsTopSpace } else { 0f } +
                squareSize * 7 + squareVerticalSpace * 6 +
                if (isDisplayLegend) {
                    legendTopSpace + if (legendPaint.getTextHeight() > legendSquareSize) { legendPaint.getHeight() } else { legendSquareSize }
                } else {
                    0f
                } + paddingBottom

        setMeasuredDimension(View.resolveSize(minWidth, widthMeasureSpec), View.resolveSize(minHeight.toInt(), heightMeasureSpec))
    }

    override fun onDraw(canvas: Canvas) {
        val offsetStart = (canvas.width - dayOfWeekPaint.getDayOfWeekWidth() - paddingStart - paddingEnd - contributionsLeftSpace) % (squareSize + squareHorizontalSpace) / 2

        if (isDisplayDayOfWeek) {
            drawDayOfWeek(
                    canvas,
                    paddingStart.toFloat(),
                    paddingTop + if (isDisplayMonth) { monthPaint.getTextHeight() } else { 0f }
            )
        }

        drawContributions(
                canvas,
                paddingStart + offsetStart + if (isDisplayDayOfWeek) { dayOfWeekPaint.getDayOfWeekWidth() + contributionsLeftSpace } else 0f,
                paddingTop + if (isDisplayMonth) { monthPaint.getTextHeight() + contributionsTopSpace } else { 0f },
                paddingEnd.toFloat()
        )

        if (isDisplayLegend) {
            drawLegend(
                    canvas,
                    paddingTop + if (isDisplayMonth) { monthPaint.getTextHeight() + contributionsTopSpace } else { 0f } + squareSize * 7 + squareVerticalSpace * 6 + legendTopSpace,
                    paddingEnd.toFloat()
            )
        }
    }

    private fun drawDayOfWeek(canvas: Canvas, spaceLeft: Float = 0f, spaceTop: Float = 0f) {
        val dayOfWeeks: List<DayOfWeek> = if (isMondayStart) {
            DayOfWeek.values().toList()
        } else {
            arrayListOf(arrayListOf(DayOfWeek.SUNDAY), DayOfWeek.values().dropLast(1)).flatMap { it }
        }

        dayOfWeeks.filter { dayOfWeekPaint.dayOfWeeks[it] != null }
                .forEach { d ->
                    val n = if (isMondayStart) {
                        d.value
                    } else {
                        val tmp = d.value + 1
                        (if (tmp > 7) 1 else tmp)
                    } - 1
                    canvas.drawText(
                            dayOfWeekPaint.dayOfWeeks[d],
                            spaceLeft,
                            spaceTop + n * squareSize + n * squareVerticalSpace + dayOfWeekPaint.getTextHeight(),
                            dayOfWeekPaint.getPaint()
                    )
                }
    }

    private fun drawLegend(canvas: Canvas, spaceTop: Float = 0f, spaceRight: Float = 0f) {
        val legendAreaWidth = legendSquareSize * 5 + legendSquareSpace * 4 + legendPaint.getLessWidth() + legendPaint.getMoreWidth() + legendTextSpace * 2
        var x = canvas.width - spaceRight - legendAreaWidth
        val diff = Math.abs(legendPaint.getTextHeight() - legendSquareSize)
        val textY = if (legendPaint.getTextHeight() > legendSquareSize) {
            spaceTop + legendPaint.getTextHeight() - legendPaint.getFontTopSpace()
        } else {
            spaceTop + legendPaint.getTextHeight() + diff / 2 - legendPaint.getFontTopSpace()
        }

        canvas.drawText(
                legendPaint.lessText,
                x,
                textY,
                legendPaint.getPaint()
        )

        x += legendPaint.getLessWidth() + legendTextSpace
        val squareY1 = if (legendPaint.getTextHeight() > legendSquareSize) {
            spaceTop + diff / 2
        } else {
            spaceTop
        }
        val squareY2 = squareY1 + legendSquareSize

        for (i in 0..4) {
            canvas.drawRect(
                    x,
                    squareY1,
                    x + legendSquareSize,
                    squareY2,
                    squarePaint.getPaint(i)
            )
            x += legendSquareSize + if (i < 4) legendSquareSpace else legendTextSpace
        }

        canvas.drawText(
                legendPaint.moreText,
                x,
                textY,
                legendPaint.getPaint()
        )

    }

    private fun drawContributions(canvas: Canvas, spaceLeft: Float = 0f, spaceTop: Float = 0f, spaceRight: Float = 0f) {
        val weeks = ((canvas.width - spaceLeft - spaceRight) / (squareSize + squareHorizontalSpace)).toInt()
        var x1 = spaceLeft
        var x2 = x1 + squareSize

        val todayDayOfWeek = if (isMondayStart) {
            endLocalDate.dayOfWeek.value
        } else {
            val tmp = endLocalDate.dayOfWeek.value + 1
            if (tmp > 7) 1 else tmp
        }

        startLocalDate = endLocalDate.minusDays(((weeks - 1) * 7 + todayDayOfWeek - 1).toLong())

        var commitDate = endLocalDate.minusWeeks((weeks - 1).toLong()).let {
            it.minusDays((it.dayOfWeek.value - if (isMondayStart) 1 else 0).toLong())
        }

        // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
        for (week in 1..weeks) {
            var y1 = spaceTop
            var y2 = y1 + squareSize
            for (n in 1..7) {
                if (week == weeks && n > todayDayOfWeek) break
                // 月の描画
                if (isDisplayMonth && commitDate.dayOfMonth == 1) drawMonth(canvas, commitDate.month, x1, paddingTop.toFloat())

                // 四角の描画
                canvas.drawRect(
                        x1,
                        y1,
                        x2,
                        y2,
                        squarePaint.getPaint(contributions[commitDate] ?: 0)
                )

                commitDate = commitDate.plusDays(1)
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
                spaceTop + monthPaint.getTextHeight(),
                monthPaint.getPaint()
        )
    }
}
