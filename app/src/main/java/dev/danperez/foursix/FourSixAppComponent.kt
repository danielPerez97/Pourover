package dev.danperez.foursix

import android.content.Context
import dev.danperez.foursix.di.AppScope
import dev.danperez.foursix.di.FourSixPresenterFactory
import dev.danperez.foursix.presenter.FourSixPresenter
import dev.danperez.foursix.views.FourSixActivity
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

@DependencyGraph(scope = AppScope::class)
interface FourSixAppComponent
{
    fun inject(activity: FourSixActivity)

    @Binds
    val FourSixPresenterFactory.presenterFactory: FourSixPresenter.Factory

    @DependencyGraph.Factory
    interface Factory
    {
        fun create(
            @Provides context: Context,
        ): FourSixAppComponent
    }
}