package dev.danperez.pourover.usersettings.internal

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.danperez.pourover.usersettings.sqlite.PouroverSettings
import dev.danperez.pourover.usersettings.sqlite.PouroverUserSettings
import dev.zacsweers.metro.Inject

@Inject
actual class SqliteDriverFactory(
    private val applicationContext: Context
) {

    actual fun createDriver(): SqlDriver
    {
        return AndroidSqliteDriver(PouroverSettings.Schema, applicationContext, "settings.db")
    }
}