package dev.danperez.foursix.di

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dev.danperez.foursixcore.FourSixProducer

@Module
@ContributesTo(AppScope::class)
class FourSixModule
{
    @Provides
    fun provideFourSixProducer(): FourSixProducer
    {
        return FourSixProducer()
    }
}