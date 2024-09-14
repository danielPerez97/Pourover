package dev.danperez.foursix

import android.app.Application

class FourSixApplication: Application()
{
    private lateinit var appComponent: FourSixAppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerFourSixAppComponent.factory().create(applicationContext)
    }
}