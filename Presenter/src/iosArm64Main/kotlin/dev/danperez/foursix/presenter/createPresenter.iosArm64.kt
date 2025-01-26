package dev.danperez.foursix.presenter

import dev.danperez.foursixcore.FourSixProducer

actual fun createPresenter(): FourSixPresenter {
    return FourSixPresenter(
        fourSixProducer = FourSixProducer()
    )
}