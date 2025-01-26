package dev.danperez.foursix.presenter.molecule

import kotlinx.coroutines.CoroutineScope

actual open class MoleculePresenter actual constructor(scope: CoroutineScope?) {
    actual val viewModelScope: CoroutineScope
        get() = TODO("Not yet implemented")
    actual val moleculeScope: CoroutineScope
        get() = TODO("Not yet implemented")

    protected actual open fun onCleared() {
    }

    protected actual open fun performSetup() {
    }

}