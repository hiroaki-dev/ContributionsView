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
    private val squareVerticalPadding = 4
    private val squareHorizontalPadding = 4

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
        val dpi = resources.displayMetrics.density


        Log.d(TAG, "dpi = $dpi")
        Log.d(TAG, "canvasSize: width = ${canvas.width}px, height = ${canvas.height}px")
        Log.d(TAG, "canvasSize: width = ${canvas.width / dpi}dp, height = ${canvas.height / dpi}dpi")

        Log.d(TAG, "count = ${canvas.width / ((squareSize + squareHorizontalPadding) * dpi).toInt()}")
        Log.d(TAG, "余り = ${canvas.width % ((squareSize + squareHorizontalPadding) * dpi)}")
        val offsetStart = canvas.width % ((squareSize + squareHorizontalPadding) * dpi) / 2
        var x1 = offsetStart
        var x2 = x1 + squareSize * dpi

        // drawRectを使って矩形を描画する、引数に座標を設定
        // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
        while(x2 < canvas.width) {
            var y1 = 0f
            var y2 = y1 + squareSize * dpi
            for (i in 0..6) {
                canvas.drawRect(
                        x1,
                        y1,
                        x2,
                        y2,
                        squarePaint
                )
                y1 = y2 + squareVerticalPadding * dpi
                y2 = y1 + squareSize * dpi
            }
            x1 = x2 + squareHorizontalPadding * dpi
            x2 = x1 + squareSize * dpi
        }
    }
}
