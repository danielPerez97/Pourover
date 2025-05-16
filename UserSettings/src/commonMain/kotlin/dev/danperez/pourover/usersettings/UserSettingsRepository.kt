package dev.danperez.pourover.usersettings

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserSettingsRepository @Inject constructor()
{
    private val userSettingsFlow = MutableStateFlow(UserSettings())

    fun getUserSettings(): StateFlow<UserSettings>
    {

        return userSettingsFlow
    }
}

data class UserSettings(
    val grams: Int = 20,
    val sweetness: Sweetness = Sweetness.Standard,
    val strength: Strength = Strength.Lighter,
)

enum class Sweetness {
    Standard,
    Sweeter,
    Brighter
}

enum class Strength {
    Lighter,
    Stronger,
    EvenStronger
}