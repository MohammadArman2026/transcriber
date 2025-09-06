package com.example.transcriber.domain.repository

import com.example.transcriber.domain.model.TranscriptionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface TranscriptionRepository {
    suspend fun getTranscibedText(
        token: String,
        file: MultipartBody.Part,
        model: RequestBody,
       language: RequestBody
    ): Result<TranscriptionResponse>?
}