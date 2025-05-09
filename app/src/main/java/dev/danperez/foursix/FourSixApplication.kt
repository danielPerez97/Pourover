package dev.danperez.foursix

import android.app.Application
import dev.zacsweers.metro.createGraph
import dev.zacsweers.metro.createGraphFactory

class FourSixApplication: Application()
{
    lateinit var appComponent: FourSixAppComponent

    override fun onCreate() {
        super.onCreate()
        val factory = createGraphFactory<FourSixAppComponent.Factory>()
        appComponent = factory.create(applicationContext)
    }
}