package dev.danperez.pourover.usersettings.internal

import android.content.Context
import dev.danperez.pourover.scopes.PouroverAppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import okio.Path
import okio.Path.Companion.toOkioPath

@ContributesBinding(PouroverAppScope::class)
@Inject
class AndroidOsPathFactory(
    private val context: Context
): OsPathFactory
{
    override fun get(): Path {
        return context.dataDir.toOkioPath()
    }
}