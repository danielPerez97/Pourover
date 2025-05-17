package dev.danperez.foursix.di

import dev.danperez.foursixcore.FourSixProducer
import dev.danperez.pourover.scopes.PouroverAppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(PouroverAppScope::class)
interface FourSixModule
{
    @Provides
    fun provideFourSixProducer(): FourSixProducer
    {
        return FourSixProducer()
    }
}