package dev.danperez.foursix.presenter

import app.cash.molecule.DisplayLinkClock
import dev.danperez.foursixcore.FourSixProducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

fun create(): FourSixPresenter {
    return FourSixPresenter(
        fourSixProducer = FourSixProducer(),
        scope = CoroutineScope(SupervisorJob()),
        dispatcher = DisplayLinkClock
    )
}