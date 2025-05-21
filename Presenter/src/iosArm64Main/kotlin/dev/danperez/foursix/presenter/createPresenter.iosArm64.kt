package dev.danperez.foursix.presenter

import app.cash.sqldelight.EnumColumnAdapter
import dev.danperez.foursixcore.FourSixProducer
import dev.danperez.pourover.usersettings.UserSettingsRepository
import dev.danperez.pourover.usersettings.internal.SqliteDriverFactory
import dev.danperez.pourover.usersettings.sqlite.PouroverSettings
import dev.danperez.pourover.usersettings.sqlite.PouroverUserSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob

actual fun createDarwinPresenter(): FourSixPresenter {
    val backgroundScope = CoroutineScope(SupervisorJob())
    val driverFactory = SqliteDriverFactory()
    val adapter = PouroverUserSettings.Adapter(
        sweetnessAdapter = EnumColumnAdapter(),
        strengthAdapter = EnumColumnAdapter()
    )
    val pouroverSettings = PouroverSettings(driverFactory.createDriver(), adapter)
    return FourSixPresenter(
        fourSixProducer = FourSixProducer(),
        scope = backgroundScope.coroutineContext,
        dispatcher = MainScope().coroutineContext,
        userSettingsRepository = UserSettingsRepository(pouroverSettings),
    )
}