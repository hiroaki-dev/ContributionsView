package me.hiroaki.contributionsview

class InvalidAmountException(private val amount: Int): RuntimeException() {
    override val message: String?
        get() = "Expected amount is 0 over. But it was $amount"
}