package com.example.transcriber.data.repository

import com.example.transcriber.data.remote.TranscriptionApiService
import com.example.transcriber.domain.model.TranscriptionResponse
import com.example.transcriber.domain.repository.TranscriptionRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class TrancriptionRepositoryImpl @Inject constructor(
    private val transcriptionApiService: TranscriptionApiService
): TranscriptionRepository {

    override suspend fun getTranscibedText(
        token: String,
        file: MultipartBody.Part,
        model: RequestBody,
        language: RequestBody
    ): Result<TranscriptionResponse>? {
      return try {
          val response=transcriptionApiService.transcribeAudio(token,file,model,language)

          if(response.isSuccessful){
              val body =response.body()
              if(body!=null){
                  Result.success(body)
              }else{
                  Result.failure(Exception("empty body"))
              }
          } else {
              Result.failure(Exception("failed"))
          }
      }catch(e: Exception){
          Result.failure(e)
      }
    }
}