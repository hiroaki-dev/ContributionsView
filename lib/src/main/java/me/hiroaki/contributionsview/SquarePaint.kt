package me.hiroaki.contributionsview

import android.graphics.Paint

class SquarePaint(
        colorOfE: Int,
        colorOfD: Int,
        colorOfC: Int,
        colorOfB: Int,
        colorOfA: Int,
        evaluations: Map<Evaluation, Int>
) {

    private val squarePaintE = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }
    private val squarePaintD = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }
    private val squarePaintC = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }
    private val squarePaintB = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }
    private val squarePaintA = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }

    var evaluations: Map<Evaluation, Int> = evaluations
        set(value) {
            if (value.size != Evaluation.values().size) {
                throw InsufficientNumberOfMapElementsException(value.size)
            }
            arrayOf(
                    value[Evaluation.E]!!,
                    value[Evaluation.D]!!,
                    value[Evaluation.C]!!,
                    value[Evaluation.B]!!,
                    value[Evaluation.A]!!
            ).reduce { acc, i ->
                if (acc >= i) throw MustBeGreaterAmountException(acc, i)
                return@reduce i
            }
            field = value
        }

    init {
        squarePaintE.color = colorOfE
        squarePaintD.color = colorOfD
        squarePaintC.color = colorOfC
        squarePaintB.color = colorOfB
        squarePaintA.color = colorOfA
    }

    fun getPaint(evaluation: Evaluation): Paint {
        return when (evaluation) {
            Evaluation.E -> squarePaintE
            Evaluation.D -> squarePaintD
            Evaluation.C -> squarePaintC
            Evaluation.B -> squarePaintB
            Evaluation.A -> squarePaintA
        }
    }

    fun getPaint(amount: Int): Paint {
        return when {
            evaluations[Evaluation.E]!! >= amount -> squarePaintE
            evaluations[Evaluation.D]!! >= amount -> squarePaintD
            evaluations[Evaluation.C]!! >= amount -> squarePaintC
            evaluations[Evaluation.B]!! >= amount -> squarePaintB
            else -> squarePaintA
        }
    }
}