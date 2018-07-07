package me.hiroaki.contributionsview

import android.graphics.Color
import android.graphics.Paint

class SquarePaint() {

    private val squarePaint0 = Paint().apply {
        color = Color.argb(255, 238, 238, 238)
        style = Paint.Style.FILL_AND_STROKE
    }
    private val squarePaint1 = Paint().apply {
        color = Color.argb(255, 204, 226, 149)
        style = Paint.Style.FILL_AND_STROKE
    }
    private val squarePaint2 = Paint().apply {
        color = Color.argb(255, 140, 198, 120)
        style = Paint.Style.FILL_AND_STROKE
    }
    private val squarePaint3 = Paint().apply {
        color = Color.argb(255, 73, 150, 71)
        style = Paint.Style.FILL_AND_STROKE
    }
    private val squarePaint4 = Paint().apply {
        color = Color.argb(255, 47, 94, 46)
        style = Paint.Style.FILL_AND_STROKE
    }

    constructor(
            colorOfNoCommit: Int,
            colorOfOneCommit: Int,
            colorOfTwoCommit: Int,
            colorOfThreeCommit: Int,
            colorOfOverFourCommit: Int
    ) : this() {
        squarePaint0.color = colorOfNoCommit
        squarePaint1.color = colorOfOneCommit
        squarePaint2.color = colorOfTwoCommit
        squarePaint3.color = colorOfThreeCommit
        squarePaint4.color = colorOfOverFourCommit
    }

    fun getPaint(amount: Int): Paint {
        if (amount < 0) throw InvalidAmountException(amount)
        return when (amount) {
            0 -> squarePaint0
            1 -> squarePaint1
            2 -> squarePaint2
            3 -> squarePaint3
            else -> squarePaint4
        }
    }
}