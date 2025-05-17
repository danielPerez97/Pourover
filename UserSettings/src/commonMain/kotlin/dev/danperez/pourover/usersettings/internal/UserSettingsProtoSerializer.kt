package dev.danperez.pourover.usersettings.internal

import androidx.datastore.core.okio.OkioSerializer
import dev.danperez.pourover.usersettings.proto.UserSettings
import okio.BufferedSink
import okio.BufferedSource
import okio.IOException

internal object UserSettingsProtoSerializer: OkioSerializer<UserSettings> {
    override val defaultValue: UserSettings
        get() = UserSettings()

    override suspend fun readFrom(source: BufferedSource): UserSettings {
        try {
            return UserSettings.ADAPTER.decode(source)
        } catch (e: IOException) {
            throw Exception(e.message ?: "Serialization Exception")
        }
    }

    override suspend fun writeTo(
        t: UserSettings,
        sink: BufferedSink
    ) {
        sink.write(t.encode())
    }

}