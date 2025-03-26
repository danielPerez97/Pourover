package dev.danperez.foursix.di

import dagger.Module
import dagger.Provides
import dev.danperez.foursixcore.FourSixProducer

@Module
class FourSixModule
{
    @Provides
    fun provideFourSixProducer(): FourSixProducer
    {
        return FourSixProducer()
    }
}