package dev.danperez.foursix

import android.app.Application
import dev.zacsweers.metro.createGraphFactory

class FourSixApplication: Application()
{
    lateinit var appComponent: FourSixDependencyGraph

    override fun onCreate() {
        super.onCreate()
        val factory = createGraphFactory<FourSixDependencyGraph.Factory>()
        appComponent = factory.create(applicationContext)
    }
}