package dev.danperez.pourover.usersettings.internal

import dev.danperez.pourover.scopes.PouroverAppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@Inject
@ContributesBinding(PouroverAppScope::class)
class IOsSimulatorArm64OsPathFactory: OsPathFactory
{
    @OptIn(ExperimentalForeignApi::class)
    override fun get(): Path {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )

        return (requireNotNull(documentDirectory).path + "/datafile").toPath()
    }
}