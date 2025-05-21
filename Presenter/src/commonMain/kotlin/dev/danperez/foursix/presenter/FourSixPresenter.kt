package dev.danperez.foursix.presenter

import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import dev.danperez.foursix.presenter.molecule.MoleculePresenter
import dev.danperez.foursixcore.FourSixProducer
import dev.danperez.pourover.usersettings.UserSettingsRepository
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

@Inject
class FourSixPresenter (
    private val fourSixProducer: FourSixProducer,
    private val userSettingsRepository: UserSettingsRepository,
    @Assisted("scope") scope: CoroutineContext,
    @Assisted("dispatcher") dispatcher: CoroutineContext
): MoleculePresenter(CoroutineScope(scope + dispatcher))
{
    private val events = MutableSharedFlow<FourSixEvent>(extraBufferCapacity = 20)

    val presenter: StateFlow<FourSixState> = moleculeScope.launchMolecule(mode = RecompositionMode.ContextClock) {
        fourSixPresenter(events, fourSixProducer, userSettingsRepository)
    }

    fun take(event: FourSixEvent) {
        if (!events.tryEmit(event)) {
            error("Event buffer overflow.")
        }
    }

    @AssistedFactory
    interface Factory
    {
        fun create(
            @Assisted("scope") scope: CoroutineContext,
            @Assisted("dispatcher") dispatcher: CoroutineContext,
        ): FourSixPresenter
    }
}