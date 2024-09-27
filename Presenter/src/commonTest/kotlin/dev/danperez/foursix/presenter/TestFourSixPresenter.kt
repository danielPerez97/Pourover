package dev.danperez.foursix.presenter

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import dev.danperez.foursixcore.FourSixProducer
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class TestFourSixPresenter {

    @Test
    fun test_calculationInitialHappens_GivenThatNoInput(): Unit = runBlocking {
        moleculeFlow(mode = RecompositionMode.Immediate) {
            fourSixPresenter(emptyFlow(), FourSixProducer())
        }.distinctUntilChanged()
            .test {
                assertEquals(
                    FourSixState(
                        grams = TextFieldValue("15"),
                        sweetness = Sweetness.Standard,
                        strength = Strength.Lighter,
                        firstHalfPours = emptyList(),
                        secondHalfPours = emptyList(),
                    ),
                    awaitItem()
                )

                // ... calculation happens automatically with no events...
                assertEquals(
                    FourSixState(
                        grams = TextFieldValue("15"),
                        sweetness = Sweetness.Standard,
                        strength = Strength.Lighter,
                        firstHalfPours = listOf(45, 45),
                        secondHalfPours = listOf(135),
                    ),
                    awaitItem()
                )

            }
    }

    @Test
    fun test_calculationHappens_WithNewInput() = runBlocking {
        val events = Channel<FourSixEvent>()
        moleculeFlow(mode = RecompositionMode.Immediate) {
            fourSixPresenter(events.receiveAsFlow(), FourSixProducer())
        }.distinctUntilChanged()
            .test {
                skipItems(2) // Initial State, Calculated State for initial state
                events.send(FourSixEvent.GramsChanged(TextFieldValue("20")))
                skipItems(1) // Update grams state, no calculation yet
                assertEquals(
                    FourSixState(
                        grams = TextFieldValue("20"),
                        sweetness = Sweetness.Standard,
                        strength = Strength.Lighter,
                        firstHalfPours = listOf(60, 60),
                        secondHalfPours = listOf(180),
                    ),
                    awaitItem()
                )

            }
    }
}