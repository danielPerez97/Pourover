package dev.danperez.pourover.usersettings

typealias SqliteStrength = dev.danperez.pourover.usersettings.sqlite.Strength
typealias SqliteSweetness = dev.danperez.pourover.usersettings.sqlite.Sweetness

fun Strength.toSqliteStrength(): SqliteStrength = when(this) {
    Strength.Lighter -> SqliteStrength.Lighter
    Strength.Stronger -> SqliteStrength.Stronger
    Strength.EvenStronger -> SqliteStrength.EvenStronger
}

fun Sweetness.toSqliteSweetness(): SqliteSweetness = when(this) {
    Sweetness.Standard -> SqliteSweetness.Standard
    Sweetness.Sweeter -> SqliteSweetness.Sweeter
    Sweetness.Brighter -> SqliteSweetness.Brighter
}

fun SqliteSweetness.toSweetness(): Sweetness = when(this) {
    SqliteSweetness.Standard -> Sweetness.Standard
    SqliteSweetness.Sweeter -> Sweetness.Sweeter
    SqliteSweetness.Brighter -> Sweetness.Brighter
}

fun SqliteStrength.toStrength(): Strength = when(this) {
    SqliteStrength.Lighter -> Strength.Lighter
    SqliteStrength.Stronger -> Strength.Stronger
    SqliteStrength.EvenStronger -> Strength.EvenStronger
}
