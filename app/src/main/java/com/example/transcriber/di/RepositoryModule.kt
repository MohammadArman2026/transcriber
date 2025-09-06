package com.example.transcriber.di

import com.example.transcriber.data.repository.TrancriptionRepositoryImpl
import com.example.transcriber.domain.repository.TranscriptionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTranscriptionRepository(
        impl: TrancriptionRepositoryImpl
    ): TranscriptionRepository

}