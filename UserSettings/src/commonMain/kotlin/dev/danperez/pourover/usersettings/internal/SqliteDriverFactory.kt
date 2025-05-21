package dev.danperez.pourover.usersettings.internal

import app.cash.sqldelight.db.SqlDriver

expect class SqliteDriverFactory {
    fun createDriver(): SqlDriver
}