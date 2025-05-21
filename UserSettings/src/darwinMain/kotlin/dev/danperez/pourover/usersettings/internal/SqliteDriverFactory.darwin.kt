package dev.danperez.pourover.usersettings.internal

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import dev.danperez.pourover.usersettings.sqlite.PouroverSettings
import dev.zacsweers.metro.Inject

@Inject
actual class SqliteDriverFactory {

    actual fun createDriver(): SqlDriver
    {
        return NativeSqliteDriver(PouroverSettings.Schema, "settings.db")
    }
}