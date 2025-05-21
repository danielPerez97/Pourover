package dev.danperez.foursix.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.danperez.foursixcore.FourSixProducer
import dev.danperez.pourover.usersettings.UserSettingsRepository
import kotlinx.coroutines.flow.Flow
import dev.danperez.foursixcore.FourSixProducer.Strength as ProducerStrength
import dev.danperez.foursixcore.FourSixProducer.Sweetness as ProducerSweetness

typealias RepoStrength = dev.danperez.pourover.usersettings.Strength
typealias RepoSweetness = dev.danperez.pourover.usersettings.Sweetness

@Composable
fun fourSixPresenter(
    events: Flow<FourSixEvent>,
    fourSixProducer: FourSixProducer,
    userSettingsRepository: UserSettingsRepository,
): FourSixState {
    val userSettingsState by userSettingsRepository.getUserSettings().collectAsState()
    var firstHalfPours by remember { mutableStateOf(emptyList<Int>()) }
    var secondHalfPours by remember { mutableStateOf(emptyList<Int>()) }

    LaunchedEffect(userSettingsState) {
        if(userSettingsState.grams > 0) {
            val results = fourSixProducer.calculate(
                gramsBeans = userSettingsState.grams.toInt(),
                sweetness = userSettingsState.sweetness.toProducerSweetness(),
                strength = userSettingsState.strength.toProducerStrength(),
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
            println(event)
            when(event) {
                is FourSixEvent.GramsChanged -> {
                    if(event.newGrams.isNotEmpty()) {
                        userSettingsRepository.updateData(
                            userSettingsState.copy(
                                grams = event.newGrams.toInt(),
                                sweetness = userSettingsState.sweetness,
                                strength = userSettingsState.strength
                            )
                        )
                    }
                }

                is FourSixEvent.SweetnessChanged -> {
                    userSettingsRepository.updateData(
                        userSettingsState.copy(sweetness = event.sweetness.toRepoSweetness())
                    )
                }

                is FourSixEvent.StrengthChanged -> {
                    userSettingsRepository.updateData(
                        userSettingsState.copy(strength = event.strength.toRepoStrength())
                    )
                }
            }
        }
    }

    return FourSixState(
        grams = userSettingsState.grams,
        sweetness = userSettingsState.sweetness.toPresenterSweetness(),
        strength = userSettingsState.strength.toPresenterStrength(),
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

fun RepoSweetness.toProducerSweetness(): ProducerSweetness = when(this) {
    RepoSweetness.Standard -> ProducerSweetness.Standard
    RepoSweetness.Sweeter -> ProducerSweetness.Sweeter
    RepoSweetness.Brighter -> ProducerSweetness.Brighter
}

fun RepoStrength.toProducerStrength(): ProducerStrength = when(this) {
    RepoStrength.Lighter -> ProducerStrength.Lighter
    RepoStrength.Stronger -> ProducerStrength.Stronger
    RepoStrength.EvenStronger -> ProducerStrength.EvenStronger
}

fun Sweetness.toRepoSweetness(): RepoSweetness = when(this) {
    Sweetness.Standard -> RepoSweetness.Standard
    Sweetness.Sweeter -> RepoSweetness.Sweeter
    Sweetness.Brighter -> RepoSweetness.Brighter
}

fun Strength.toRepoStrength(): RepoStrength = when(this) {
    Strength.Lighter -> RepoStrength.Lighter
    Strength.Stronger -> RepoStrength.Stronger
    Strength.EvenStronger -> RepoStrength.EvenStronger
}