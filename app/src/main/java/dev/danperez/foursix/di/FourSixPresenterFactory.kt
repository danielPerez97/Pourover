package dev.danperez.foursix.di

import dagger.Binds
import dagger.Module
import dev.danperez.foursix.presenter.FourSixPresenter
import dev.danperez.foursixcore.FourSixProducer
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider
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
            fourSixProducer = producer.get(),
            scope = CoroutineScope(dispatcher)
        )
    }

}

@Module
interface FourSixPresenterFactoryBinder
{
    @Binds
    fun bind(impl: FourSixPresenterFactory): FourSixPresenter.Factory
}