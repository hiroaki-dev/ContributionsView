package me.hiroaki.contributionsview

class MustBeGreaterAmountException(private val lessAmount: Int, private val moreAmount: Int): RuntimeException() {
    override val message: String?
        get() = "Expected amount is $lessAmount over. But it was $moreAmount"
}