package dev.danperez.foursix.presenter

import dev.danperez.foursixcore.FourSixProducer
import dev.danperez.pourover.scopes.PouroverAppScope
import dev.danperez.pourover.usersettings.UserSettingsRepository
import dev.danperez.pourover.usersettings.internal.DataStoreFactory
import dev.danperez.pourover.usersettings.internal.IOsSimulatorArm64OsPathFactory
import dev.danperez.pourover.usersettings.internal.OsPathFactory
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask


actual fun createDarwinPresenter(): FourSixPresenter {
    val backgroundScope = CoroutineScope(SupervisorJob())
    val osPathFactory = IOsSimulatorArm64OsPathFactory()
    val dataStoreFactory = DataStoreFactory(osPathFactory)
    return FourSixPresenter(
        fourSixProducer = FourSixProducer(),
        scope = backgroundScope.coroutineContext,
        dispatcher = MainScope().coroutineContext,
        userSettingsRepository = UserSettingsRepository(dataStoreFactory),
    )
}


