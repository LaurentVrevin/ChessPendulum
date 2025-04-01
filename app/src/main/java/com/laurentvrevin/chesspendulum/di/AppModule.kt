package com.laurentvrevin.chesspendulum.di

import com.laurentvrevin.chesspendulum.domain.usecase.FormatTimeUseCase
import com.laurentvrevin.chesspendulum.domain.usecase.TimeManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFormatTimeUseCase(): FormatTimeUseCase = FormatTimeUseCase()

    @Provides
    fun provideTimeManager(): TimeManager = TimeManager(0L)
}