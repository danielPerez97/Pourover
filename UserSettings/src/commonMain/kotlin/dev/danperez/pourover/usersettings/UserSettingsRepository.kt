package dev.danperez.pourover.usersettings

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import dev.danperez.pourover.usersettings.sqlite.PouroverSettings
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Inject
class UserSettingsRepository(
    private val pouroverSettings: PouroverSettings
) {
    private val scope = CoroutineScope(SupervisorJob())
    private val state: MutableStateFlow<UserSettings> = MutableStateFlow(UserSettings())

    init {
        scope.launch(Dispatchers.IO) {
            pouroverSettings
                .pouroverUserSettingsQueries
                .getSettings()
                .asFlow()
                .mapToOne(Dispatchers.IO)
                .map {
                    UserSettings(
                        grams = it.grams.toInt(),
                        sweetness = it.sweetness.toSweetness(),
                        strength = it.strength.toStrength()
                    )
                }
                .collect { newState ->
                    println("collect: $newState")
                    state.update { newState }
                }
        }
    }

    fun getUserSettings(): StateFlow<UserSettings> = state

    fun updateData(userSettings: UserSettings) {
        with(userSettings) {
            pouroverSettings.pouroverUserSettingsQueries.upsert(
                grams = grams.toLong(),
                sweetness = sweetness.toSqliteSweetness(),
                strength = strength.toSqliteStrength()
            )
        }
    }
}