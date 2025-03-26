package dev.danperez.foursix.views

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AndroidUiDispatcher
import dev.danperez.foursix.FourSixApplication
import dev.danperez.foursix.frontend.Screen
import dev.danperez.foursix.presenter.FourSixPresenter
import dev.danperez.foursix.views.theme.FourSixTheme
import dev.marcellogalhardo.retained.activity.retain
import javax.inject.Inject

class FourSixActivity: AppCompatActivity()
{
    @Inject lateinit var presenterFactory: FourSixPresenter.Factory
    private val presenter: FourSixPresenter by retain {
        presenterFactory.create(it.scope, AndroidUiDispatcher.Main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as FourSixApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            val state by presenter.presenter.collectAsState()

            FourSixTheme {
                Screen(
                    model = state,
                    onEvent = { presenter.take(it) },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}