package dev.danperez.foursix.presenter

import dev.danperez.foursixcore.FourSixProducer
import dev.danperez.pourover.usersettings.UserSettingsRepository
import dev.danperez.pourover.usersettings.internal.DataStoreFactory
import dev.danperez.pourover.usersettings.internal.IOsArm64OsPathFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob

actual fun createDarwinPresenter(): FourSixPresenter {
    val backgroundScope = CoroutineScope(SupervisorJob())
    val osPathFactory = IOsArm64OsPathFactory()
    val dataStoreFactory = DataStoreFactory(osPathFactory)
    return FourSixPresenter(
        fourSixProducer = FourSixProducer(),
        scope = backgroundScope.coroutineContext,
        dispatcher = MainScope().coroutineContext,
        userSettingsRepository = UserSettingsRepository(dataStoreFactory),
    )
}