package dev.danperez.foursix.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.danperez.foursix.molecule.MoleculePresenter
import dev.danperez.foursixcore.FourSixProducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class FourSixPresenter @AssistedInject constructor(
    private val fourSixProducer: FourSixProducer,
    @Assisted scope: CoroutineScope,
    @Assisted dispatcher: CoroutineContext,
): MoleculePresenter<FourSixEvent, FourSixState>(scope.coroutineContext, dispatcher)
{
    @Composable
    override fun models(events: Flow<FourSixEvent>): FourSixState {
        var grams by remember { mutableStateOf(TextFieldValue("15")) }
        var sweetness by remember { mutableStateOf(Sweetness.Standard) }
        var strength by remember { mutableStateOf(Strength.Lighter) }
        var firstHalfPours by remember { mutableStateOf(emptyList<Int>()) }
        var secondHalfPours by remember { mutableStateOf(emptyList<Int>()) }

        fun calculate(newGrams: String) {
            if(newGrams.isNotEmpty()) {
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

        LaunchedEffect(Unit) {
            calculate(newGrams = grams.text)
            events.collect { event ->
                when(event) {
                    is FourSixEvent.GramsChanged -> {
                        grams = event.newGrams
                        calculate(newGrams = grams.text)
                    }

                    is FourSixEvent.SweetnessChanged -> {
                        sweetness = event.sweetness
                        calculate(newGrams = grams.text)
                    }

                    is FourSixEvent.StrengthChanged -> {
                        strength = event.strength
                        calculate(newGrams = grams.text)
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

    @AssistedFactory
    interface Factory
    {
        fun create(
            coroutineContext: CoroutineScope,
            dispatcher: CoroutineContext,
        ): FourSixPresenter
    }
}