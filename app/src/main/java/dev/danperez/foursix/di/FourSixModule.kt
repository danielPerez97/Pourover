package dev.danperez.foursix.di

import dev.danperez.foursixcore.FourSixProducer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
interface FourSixModule
{
    @Provides
    fun provideFourSixProducer(): FourSixProducer
    {
        return FourSixProducer()
    }
}