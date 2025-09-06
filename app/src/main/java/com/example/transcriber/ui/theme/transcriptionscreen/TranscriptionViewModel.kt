package com.example.transcriber.ui.theme.transcriptionscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transcriber.di.ApiKey
import com.example.transcriber.domain.repository.TranscriptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class TranscriptionViewModel @Inject constructor(
    private val transcriptionRepository:
    TranscriptionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TranscriptionUiState())
    val uiState = _uiState.asStateFlow()


    fun getTranscribedText(
//        token: String,
        file: File,
//model: RequestBody,
//        language: RequestBody
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            val languagePart = "en".toRequestBody("text/plain".toMediaTypeOrNull())
            val requestFile: RequestBody = RequestBody.create("audio/m4a".toMediaTypeOrNull(), file)
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val modelPart = RequestBody.create("text/plain".toMediaTypeOrNull(), "whisper-large-v3")


            val result = transcriptionRepository
                .getTranscibedText(
                    token = "Bearer ${ApiKey.apiKey}",
                    filePart,
                    modelPart,
                    languagePart
                )

            result?.onSuccess { response ->
                _uiState.update {
                    it.copy(
                        success = response.text,
                        isLoading = false
                    )
                }
            }
                ?.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            success = error.message ?: "some error",
                            isLoading = false
                        )
                    }
                }
        }
    }
}