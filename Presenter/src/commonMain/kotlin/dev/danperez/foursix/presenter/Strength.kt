package dev.danperez.foursix.presenter

enum class Strength {
    /** Lighter strength profile: Uses the entire 60% of water in a single pour. */
    Lighter,

    /** Stronger profile: Splits the 60% of water into two equal pours. */
    Stronger,

    /** Even stronger profile: Splits the 60% of water into three equal pours. */
    EvenStronger
}