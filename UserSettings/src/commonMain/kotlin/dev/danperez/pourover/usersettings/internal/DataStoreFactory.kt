package dev.danperez.pourover.usersettings.internal

import dev.zacsweers.metro.Inject
import okio.FileSystem

@Inject
class DataStoreFactory constructor(
    private val osPathFactory: OsPathFactory
)
{
    fun get() = createDataStore(
        fileSystem = getPlatformFileSystem(),
        osPathFactory = osPathFactory,
    )
}

expect fun getPlatformFileSystem(): FileSystem