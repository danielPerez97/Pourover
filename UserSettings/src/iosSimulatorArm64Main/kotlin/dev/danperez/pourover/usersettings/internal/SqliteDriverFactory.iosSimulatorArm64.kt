package dev.danperez.pourover.usersettings.internal

import app.cash.sqldelight.db.SqlDriver
import dev.danperez.pourover.usersettings.sqlite.PouroverSettings
import dev.zacsweers.metro.Inject

@Inject
actual class SqliteDriverFactory {

    actual fun createDriver(): SqlDriver
    {
        TODO()
    }
}