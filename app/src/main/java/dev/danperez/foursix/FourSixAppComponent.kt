package dev.danperez.foursix

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dev.danperez.foursix.di.FourSixModule
import dev.danperez.foursix.di.FourSixPresenterFactoryBinder
import dev.danperez.foursix.views.FourSixActivity

@Component(modules = [FourSixModule::class, FourSixPresenterFactoryBinder::class])
interface FourSixAppComponent
{
    fun inject(activity: FourSixActivity)

    @Component.Factory
    interface Factory
    {
        fun create(
            @BindsInstance context: Context,
        ): FourSixAppComponent
    }
}