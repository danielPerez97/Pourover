package dev.danperez.foursix.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import dev.danperez.foursixcore.FourSixProducer
import kotlinx.coroutines.flow.Flow

@Composable
fun fourSixPresenter(events: Flow<FourSixEvent>, fourSixProducer: FourSixProducer): FourSixState {
    var grams by remember { mutableStateOf(TextFieldValue("15")) }
    var sweetness by remember { mutableStateOf(Sweetness.Standard) }
    var strength by remember { mutableStateOf(Strength.Lighter) }
    var firstHalfPours by remember { mutableStateOf(emptyList<Int>()) }
    var secondHalfPours by remember { mutableStateOf(emptyList<Int>()) }

    LaunchedEffect(grams) {
        if(grams.text.isNotEmpty()) {
            val results = fourSixProducer.calculate(
                gramsBeans = grams.text.toInt(),
                sweetness = sweetness.toSweetness(),
                strength = strength.toStrength(),
            )
            firstHalfPours = results.first
            secondHalfPours = results.second
        } else {
            firstHalfPours = emptyList()
            secondHalfPours = emptyList()
        }
    }

    LaunchedEffect(events) {
        events.collect { event ->
            when(event) {
                is FourSixEvent.GramsChanged -> {
                    grams = event.newGrams
                }

                is FourSixEvent.SweetnessChanged -> {
                    sweetness = event.sweetness
                }

                is FourSixEvent.StrengthChanged -> {
                    strength = event.strength
                }
            }
        }
    }

    return FourSixState(
        grams = grams,
        sweetness = sweetness,
        strength = strength,
        firstHalfPours = firstHalfPours,
        secondHalfPours = secondHalfPours,
    )
}

fun Sweetness.toSweetness(): FourSixProducer.Sweetness = when(this) {
    Sweetness.Standard -> FourSixProducer.Sweetness.Standard
    Sweetness.Sweeter -> FourSixProducer.Sweetness.Sweeter
    Sweetness.Brighter -> FourSixProducer.Sweetness.Brighter
}

fun Strength.toStrength(): FourSixProducer.Strength = when(this) {
    Strength.Lighter -> FourSixProducer.Strength.Lighter
    Strength.Stronger -> FourSixProducer.Strength.Stronger
    Strength.EvenStronger -> FourSixProducer.Strength.EvenStronger
}