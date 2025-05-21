package dev.danperez.foursix.presenter

data class FourSixState(
    val grams: Int,
    val sweetness: Sweetness,
    val strength: Strength,
    val firstHalfPours: List<Int> = emptyList(),
    val secondHalfPours: List<Int> = emptyList(),
)