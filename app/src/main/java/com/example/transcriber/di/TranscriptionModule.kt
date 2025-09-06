package com.example.transcriber.di

import com.example.transcriber.data.remote.TranscriptionApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TranscriptionModule {

    private const val BASE_URL = "https://api.groq.com/openai/v1/"

    @Provides
    @Singleton
    fun provideApiService(): TranscriptionApiService {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TranscriptionApiService::class.java)
    }

}