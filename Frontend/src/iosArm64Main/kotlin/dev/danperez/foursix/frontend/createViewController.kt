package dev.danperez.foursix.frontend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import dev.danperez.foursix.presenter.FourSixState
import dev.danperez.foursix.presenter.createPresenter
import platform.UIKit.UIViewController

fun createViewController(state: FourSixState): UIViewController = ComposeUIViewController {
    val presenter = createPresenter()
    Box(Modifier.fillMaxSize()) {
        Screen(
            model = state,
            onEvent = { presenter.take(it) },
            modifier = Modifier.fillMaxSize(),
        )
    }
}