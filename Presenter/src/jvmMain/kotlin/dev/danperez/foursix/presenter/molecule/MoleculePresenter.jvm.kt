package dev.danperez.foursix.presenter.molecule

import kotlinx.coroutines.CoroutineScope

actual open class MoleculePresenter actual constructor(scope: CoroutineScope?) {
    actual val viewModelScope: CoroutineScope = scope!!
    actual val moleculeScope: CoroutineScope = CoroutineScope(viewModelScope.coroutineContext)

    protected actual open fun onCleared() {
    }

    protected actual open fun performSetup() {
    }

}