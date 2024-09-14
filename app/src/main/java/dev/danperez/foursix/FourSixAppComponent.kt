package dev.danperez.foursix

import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import dev.danperez.foursix.di.AppScope

@MergeComponent(AppScope::class)
interface FourSixAppComponent
{

    @Component.Factory
    interface Factory
    {
        fun create(
            @BindsInstance context: Context,
        ): FourSixAppComponent
    }
}