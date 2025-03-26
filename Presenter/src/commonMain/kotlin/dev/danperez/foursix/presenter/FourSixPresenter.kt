package dev.danperez.foursix.presenter

import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import dev.danperez.foursix.presenter.molecule.MoleculePresenter
import dev.danperez.foursixcore.FourSixProducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.coroutines.CoroutineContext

class FourSixPresenter(
    private val fourSixProducer: FourSixProducer,
    scope: CoroutineScope
): MoleculePresenter(scope)
{
    private val events = MutableSharedFlow<FourSixEvent>(extraBufferCapacity = 20)

    val presenter = moleculeScope.launchMolecule(mode = RecompositionMode.ContextClock) {
        fourSixPresenter(events, fourSixProducer)
    }

    fun take(event: FourSixEvent) {
        if (!events.tryEmit(event)) {
            error("Event buffer overflow.")
        }
    }
    interface Factory
    {
        fun create(
            coroutineContext: CoroutineScope,
            dispatcher: CoroutineContext,
        ): FourSixPresenter
    }
}