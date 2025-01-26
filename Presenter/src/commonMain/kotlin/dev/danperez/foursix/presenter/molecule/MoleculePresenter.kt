package dev.danperez.foursix.presenter.molecule

import androidx.compose.runtime.Composable
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

expect open class MoleculePresenter(scope: CoroutineScope? = null) {
    val viewModelScope: CoroutineScope
    val moleculeScope: CoroutineScope

    protected open fun onCleared()
    protected open fun performSetup()
}