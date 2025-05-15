package dev.danperez.foursix.di

import dev.danperez.foursix.presenter.FourSixPresenter
import dev.danperez.foursixcore.FourSixProducer
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.Provides
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class FourSixPresenterFactory @Inject constructor(
    private val producer: Provider<FourSixProducer>
): FourSixPresenter.Factory
{
    override fun create(
        coroutineContext: CoroutineScope,
        dispatcher: CoroutineContext
    ): FourSixPresenter {
        return FourSixPresenter(
            fourSixProducer = producer.invoke(),
            scope = CoroutineScope(dispatcher)
        )
    }

}