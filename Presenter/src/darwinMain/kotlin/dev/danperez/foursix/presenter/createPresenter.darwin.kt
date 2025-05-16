package dev.danperez.foursix.presenter

import dev.danperez.foursixcore.FourSixProducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope

actual fun createPresenter(): FourSixPresenter {
    val backgroundScope: CoroutineScope = CoroutineScope(SupervisorJob())
    return FourSixPresenter(
        fourSixProducer = FourSixProducer(),
        scope = backgroundScope.coroutineContext,
        dispatcher = MainScope().coroutineContext
    )
}