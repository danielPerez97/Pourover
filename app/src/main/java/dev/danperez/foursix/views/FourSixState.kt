package dev.danperez.foursix.views

import androidx.compose.ui.text.input.TextFieldValue

data class FourSixState(
    val grams: TextFieldValue = TextFieldValue(),
    val sweetness: Sweetness,
    val strength: Strength,
    val firstHalfPours: List<Int> = emptyList(),
    val secondHalfPours: List<Int> = emptyList(),
)