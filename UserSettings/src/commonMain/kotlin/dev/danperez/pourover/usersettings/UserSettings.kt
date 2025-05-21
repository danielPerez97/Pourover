package dev.danperez.pourover.usersettings

data class UserSettings(
    val grams: Int = 20,
    val sweetness: Sweetness = Sweetness.Standard,
    val strength: Strength = Strength.Lighter,
)

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
