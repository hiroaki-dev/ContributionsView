package me.hiroaki.contributionsview

import android.graphics.Paint

class SquarePaint(
        colorOfNoCommit: Int,
        colorOfOneCommit: Int,
        colorOfTwoCommit: Int,
        colorOfThreeCommit: Int,
        colorOfOverFourCommit: Int
) {

    private val squarePaint0 = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }
    private val squarePaint1 = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }
    private val squarePaint2 = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }
    private val squarePaint3 = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }
    private val squarePaint4 = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }

    init {
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