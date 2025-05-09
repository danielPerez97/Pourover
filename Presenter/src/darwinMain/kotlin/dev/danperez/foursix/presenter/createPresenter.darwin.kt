package dev.danperez.foursix.presenter

import dev.danperez.foursixcore.FourSixProducer
import kotlinx.coroutines.MainScope

actual fun createPresenter(): FourSixPresenter {
    return FourSixPresenter(
        fourSixProducer = FourSixProducer(),
        scope = MainScope()
    )
}