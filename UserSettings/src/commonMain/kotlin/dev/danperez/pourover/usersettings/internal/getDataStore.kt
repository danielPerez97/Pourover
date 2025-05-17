package dev.danperez.pourover.usersettings.internal

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import dev.danperez.pourover.usersettings.proto.UserSettings
import okio.FileSystem

fun createDataStore(
    fileSystem: FileSystem,
    osPathFactory: OsPathFactory
): DataStore<UserSettings> = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = fileSystem,
        producePath = { osPathFactory.get() },
        serializer = UserSettingsProtoSerializer
    )
)
