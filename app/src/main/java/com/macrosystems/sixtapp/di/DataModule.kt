package com.macrosystems.sixtapp.di

import com.macrosystems.sixtapp.data.CarRepositoryImpl
import com.macrosystems.sixtapp.data.network.CarApi
import com.macrosystems.sixtapp.data.network.CarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideCarRepository(carApi: CarApi): CarRepository{
        return CarRepositoryImpl(carApi)
    }
}