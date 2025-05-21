package dev.danperez.pourover.usersettings.internal.di

import app.cash.sqldelight.EnumColumnAdapter
import dev.danperez.pourover.scopes.PouroverAppScope
import dev.danperez.pourover.usersettings.internal.SqliteDriverFactory
import dev.danperez.pourover.usersettings.sqlite.PouroverSettings
import dev.danperez.pourover.usersettings.sqlite.PouroverUserSettings
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(PouroverAppScope::class)
interface SettingsDatabaseModule {
    @Provides
    fun provideSettingsDatabase(sqliteDriverFactory: SqliteDriverFactory): PouroverSettings {
        val adapter = PouroverUserSettings.Adapter(
            sweetnessAdapter = EnumColumnAdapter(),
            strengthAdapter = EnumColumnAdapter()
        )

        val driver = sqliteDriverFactory.createDriver()
        return PouroverSettings(driver, adapter)
    }
}