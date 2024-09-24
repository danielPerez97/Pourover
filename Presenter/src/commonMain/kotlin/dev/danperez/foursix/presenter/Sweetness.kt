package dev.danperez.foursix.presenter

enum class Sweetness {
    /** Standard sweetness profile: Splits the first 40% of water into two equal pours. */
    Standard,

    /** Sweeter profile: Uses 41.67% of the total water for the first pour. */
    Sweeter,

    /** Brighter profile: Uses 58.33% of the total water for the first pour. */
    Brighter;
}