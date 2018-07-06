package me.hiroaki.contributionsview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View



class ContributionsView : View {

    private val squareSize = 12
    private val squarePaint = Paint().apply {
        color = Color.argb(255, 255, 0, 255)
        style = Paint.Style.FILL_AND_STROKE
    }
    private val squarePaddingStart = 2
    private val squarePaddingEnd = 2
    private  val squarePaddingTop = 2
    private val squarePaddingBottom = 2

    private companion object {
        val TAG = ContributionsView::class.java.simpleName
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val dp = resources.displayMetrics.density
        var x = 0f
        var y = 0f

        Log.d(TAG, "dp = $dp")
        Log.d(TAG, "canvasSize: width = ${canvas.width}px, height = ${canvas.height}px")
        Log.d(TAG, "canvasSize: width = ${canvas.width / dp}dp, height = ${canvas.height / dp}dp")


        // drawRectを使って矩形を描画する、引数に座標を設定
        // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
        canvas.drawRect(
                x + squarePaddingStart * dp,
                y + squarePaddingTop * dp,
                x + (squarePaddingStart + squareSize + squarePaddingEnd) * dp,
                y + (squarePaddingTop + squareSize + squarePaddingBottom) * dp,
                squarePaint
        )
        Log.d(TAG, "drawSize: width = ${300f / dp}dp, height = ${300f / dp}dp")
    }
}
