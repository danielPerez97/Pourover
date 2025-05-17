package dev.danperez.foursix.presenter

actual fun createPresenter(): FourSixPresenter {
    return createDarwinPresenter()
}