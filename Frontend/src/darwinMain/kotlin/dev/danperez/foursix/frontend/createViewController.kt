package dev.danperez.foursix.frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import dev.danperez.foursix.presenter.createPresenter
import androidx.compose.runtime.getValue
import platform.UIKit.UIViewController

fun createViewController(): UIViewController {
    val presenter = createPresenter()

    return ComposeUIViewController {
        val state by presenter.presenter.collectAsState()

        Screen(
            model = state,
            onEvent = { presenter.take(it) },
            modifier = Modifier.fillMaxSize(),
        )
    }
}