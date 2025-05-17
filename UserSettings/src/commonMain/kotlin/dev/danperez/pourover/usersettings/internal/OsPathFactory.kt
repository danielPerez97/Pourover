package dev.danperez.pourover.usersettings.internal

import okio.Path

interface OsPathFactory
{
    fun get(): Path
}