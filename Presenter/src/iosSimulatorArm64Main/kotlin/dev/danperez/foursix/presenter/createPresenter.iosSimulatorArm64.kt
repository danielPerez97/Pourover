package dev.danperez.foursix.presenter

import dev.danperez.foursixcore.FourSixProducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

actual fun createPresenter(): FourSixPresenter {
    return FourSixPresenter(
        fourSixProducer = FourSixProducer(),
        scope = CoroutineScope(SupervisorJob())
    )
}