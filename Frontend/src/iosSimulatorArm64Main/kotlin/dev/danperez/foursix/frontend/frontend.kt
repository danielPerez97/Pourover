package dev.danperez.foursix.frontend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import dev.danperez.foursix.presenter.FourSixPresenter
import dev.danperez.foursix.presenter.FourSixState
import platform.UIKit.UIViewController

fun FourSixSimulatorViewController(state: FourSixState, presenter: FourSixPresenter): UIViewController = ComposeUIViewController {
    Box(Modifier.fillMaxSize()) {
        Screen(
            model = state,
            onEvent = { presenter.take(it) },
            modifier = Modifier.fillMaxSize(),
        )
    }
}