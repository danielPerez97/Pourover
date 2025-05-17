package dev.danperez.foursix

import android.content.Context
import dev.danperez.pourover.scopes.PouroverAppScope
import dev.danperez.foursix.views.FourSixActivity
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

@DependencyGraph(scope = PouroverAppScope::class)
interface FourSixDependencyGraph
{
    fun inject(activity: FourSixActivity)

//    @Binds
//    val FourSixPresenterFactory.presenterFactory: FourSixPresenter.Factory

    @DependencyGraph.Factory
    interface Factory
    {
        fun create(
            @Provides context: Context,
        ): FourSixDependencyGraph
    }
}