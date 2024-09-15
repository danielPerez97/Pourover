package dev.danperez.foursix

import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import dev.danperez.foursix.di.AppScope
import dev.danperez.foursix.views.FourSixActivity

@MergeComponent(AppScope::class)
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