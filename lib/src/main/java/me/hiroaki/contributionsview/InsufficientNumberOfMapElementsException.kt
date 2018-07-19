package me.hiroaki.contributionsview

class InsufficientNumberOfMapElementsException(private val amount: Int): RuntimeException() {
    override val message: String?
        get() = "Expected number of map is 5. But it was $amount"
}