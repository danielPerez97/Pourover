package dev.danperez.foursix.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import dev.danperez.foursixcore.FourSixProducer
import dev.danperez.foursixcore.FourSixProducer.Strength as ProducerStrength
import dev.danperez.foursixcore.FourSixProducer.Sweetness as ProducerSweetness
import dev.danperez.pourover.usersettings.UserSettingsRepository
import kotlinx.coroutines.flow.Flow

import dev.danperez.pourover.usersettings.Strength as RepoStrength
import dev.danperez.pourover.usersettings.Sweetness as RepoSweetness

@Composable
fun fourSixPresenter(
    events: Flow<FourSixEvent>,
    fourSixProducer: FourSixProducer,
    userSettingsRepository: UserSettingsRepository
): FourSixState {
    val initialUserSettings by userSettingsRepository.getUserSettings().collectAsState()
    var grams: TextFieldValue by remember { mutableStateOf(TextFieldValue(initialUserSettings.grams.toString())) }
    var sweetness by remember { mutableStateOf(initialUserSettings.sweetness.toPresenterSweetness()) }
    var strength by remember { mutableStateOf(initialUserSettings.strength.toPresenterStrength()) }
    var firstHalfPours by remember { mutableStateOf(emptyList<Int>()) }
    var secondHalfPours by remember { mutableStateOf(emptyList<Int>()) }

    LaunchedEffect(grams, sweetness, strength) {
        if(grams.text.isNotEmpty()) {
            val results = fourSixProducer.calculate(
                gramsBeans = grams.text.toInt(),
                sweetness = sweetness.toProducerSweetness(),
                strength = strength.toProducerStrength(),
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

fun RepoSweetness.toPresenterSweetness(): Sweetness = when(this) {
    RepoSweetness.Standard -> Sweetness.Standard
    RepoSweetness.Sweeter -> Sweetness.Sweeter
    RepoSweetness.Brighter -> Sweetness.Brighter
}

fun RepoStrength.toPresenterStrength(): Strength = when(this) {
    RepoStrength.Lighter -> Strength.Lighter
    RepoStrength.Stronger -> Strength.Stronger
    RepoStrength.EvenStronger -> Strength.EvenStronger
}

fun Sweetness.toProducerSweetness(): ProducerSweetness = when(this) {
    Sweetness.Standard -> ProducerSweetness.Standard
    Sweetness.Sweeter -> ProducerSweetness.Sweeter
    Sweetness.Brighter -> ProducerSweetness.Brighter
}

fun Strength.toProducerStrength(): ProducerStrength = when(this) {
    Strength.Lighter -> ProducerStrength.Lighter
    Strength.Stronger -> ProducerStrength.Stronger
    Strength.EvenStronger -> ProducerStrength.EvenStronger
}