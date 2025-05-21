package dev.danperez.pourover.usersettings.sqlite

enum class Sweetness {
    Standard,
    Sweeter,
    Brighter
}

enum class Strength {
    Lighter,
    Stronger,
    EvenStronger
}
fun thing() {
    Strength.EvenStronger.name
}