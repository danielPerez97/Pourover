package dev.danperez.foursix.presenter.molecule

import app.cash.molecule.DisplayLinkClock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

actual open class MoleculePresenter actual constructor(scope: CoroutineScope?) {
    actual val viewModelScope = scope ?: MainScope()
    actual val moleculeScope = CoroutineScope(viewModelScope.coroutineContext + DisplayLinkClock)

    /**
     * Override this to do any cleanup immediately before the internal [CoroutineScope][kotlinx.coroutines.CoroutineScope]
     * is cancelled in [clear]
     */
    protected actual open fun onCleared() {
        /* Default No-Op */
    }

    /**
     * Cancels the internal [CoroutineScope][kotlinx.coroutines.CoroutineScope]. After this is called, the ViewModel should
     * no longer be used.
     */
    fun clear() {
        onCleared()
        viewModelScope.cancel()
    }

    protected actual open fun performSetup() {
        /* Overridden where needed */
    }
}