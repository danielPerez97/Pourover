package dev.danperez.foursix.views

import androidx.compose.ui.text.input.TextFieldValue

sealed interface FourSixEvent
{
    class SweetnessChanged(val sweetness: Sweetness): FourSixEvent

    class StrengthChanged(val strength: Strength): FourSixEvent

    class GramsChanged(val newGrams: TextFieldValue) : FourSixEvent
}