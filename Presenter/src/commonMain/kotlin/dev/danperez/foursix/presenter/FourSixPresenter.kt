package dev.danperez.foursix.presenter

import androidx.compose.runtime.Composable
import dev.danperez.foursix.presenter.molecule.MoleculePresenter
import dev.danperez.foursixcore.FourSixProducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class FourSixPresenter constructor(
    private val fourSixProducer: FourSixProducer,
    scope: CoroutineScope,
    dispatcher: CoroutineContext,
): MoleculePresenter<FourSixEvent, FourSixState>(scope.coroutineContext, dispatcher)
{

    @Composable
    override fun models(events: Flow<FourSixEvent>): FourSixState {
        return fourSixPresenter(
            events = events,
            fourSixProducer = fourSixProducer,
        )
    }

    interface Factory
    {
        fun create(
            coroutineContext: CoroutineScope,
            dispatcher: CoroutineContext,
        ): FourSixPresenter
    }
}