package dev.danperez.pourover.usersettings

import androidx.datastore.core.DataStore
import dev.danperez.pourover.usersettings.internal.DataStoreFactory
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserSettingsRepository @Inject constructor(
    private val dataStoreFactory: DataStoreFactory
)
{
    private val dataStore: DataStore<dev.danperez.pourover.usersettings.proto.UserSettings> = dataStoreFactory.get()
    private val userSettingsFlow = MutableStateFlow(UserSettings())

    fun getUserSettings(): StateFlow<UserSettings>
    {

        return userSettingsFlow
    }

    suspend fun getUserSettingsFromFileSystem()
    {
        dataStore.data
    }

    suspend fun updateData(grams: Int, sweetness: Sweetness, strength: Strength) {
        dataStore.updateData { it ->
            it.copy(
                grams = grams,
                sweetness = sweetness.toProtoSweetness(),
                strength = strength.toProtoStrength()
            )
        }
    }
}

private fun Strength.toProtoStrength(): dev.danperez.pourover.usersettings.proto.Strength = when(this) {
    Strength.Lighter -> dev.danperez.pourover.usersettings.proto.Strength.Lighter
    Strength.Stronger -> dev.danperez.pourover.usersettings.proto.Strength.Stronger
    Strength.EvenStronger -> dev.danperez.pourover.usersettings.proto.Strength.EvenStronger
}

private fun Sweetness.toProtoSweetness(): dev.danperez.pourover.usersettings.proto.Sweetness = when(this) {
    Sweetness.Standard -> dev.danperez.pourover.usersettings.proto.Sweetness.Standard
    Sweetness.Sweeter -> dev.danperez.pourover.usersettings.proto.Sweetness.Sweeter
    Sweetness.Brighter -> dev.danperez.pourover.usersettings.proto.Sweetness.Brighter
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