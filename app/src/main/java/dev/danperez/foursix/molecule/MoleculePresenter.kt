package dev.danperez.foursix.molecule

import androidx.compose.runtime.Composable
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

abstract class MoleculePresenter<Event, Model>(
    coroutineContext: CoroutineContext,
    dispatcher: CoroutineContext,
) {
    private val scope = CoroutineScope(coroutineContext + dispatcher)

    // Events have a capacity large enough to handle simultaneous UI events, but
    // small enough to surface issues if they get backed up for some reason.
    private val events = MutableSharedFlow<Event>(extraBufferCapacity = 20)

    val models: StateFlow<Model> by lazy(LazyThreadSafetyMode.NONE) {
        scope.launchMolecule(mode = RecompositionMode.ContextClock) {
            models(events)
        }
    }

    fun take(event: Event) {
        if (!events.tryEmit(event)) {
            error("Event buffer overflow.")
        }
    }

    @Composable
    protected abstract fun models(events: Flow<Event>): Model
}