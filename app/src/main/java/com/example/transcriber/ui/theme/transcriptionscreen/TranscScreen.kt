package com.example.transcriber.ui.theme.transcriptionscreen

import android.Manifest
import android.media.MediaRecorder
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.transcriber.R
import java.io.File


@Composable
fun TranscribeScreen(
    viewModel: TranscriptionViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }
    var recorder: MediaRecorder? by remember { mutableStateOf(null) }
    var audioFile: File? by remember { mutableStateOf(null) }
//    var isUploading by remember { mutableStateOf(false) }

    val transcribeUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val recordAudioPermission = Manifest.permission.RECORD_AUDIO
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Microphone permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    fun startRecording() {
        audioFile = File(context.cacheDir, "recording_${System.currentTimeMillis()}.m4a")
        try {
            recorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioFile!!.absolutePath)
                prepare()
                start()
            }
            isRecording = true
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to start recording", Toast.LENGTH_SHORT).show()
        }
    }

    fun stopRecordingAndUpload() {
        try {
            recorder?.apply {
                stop()
                release()
            }
            recorder = null
            isRecording = false

            audioFile?.let { file ->
//                isUploading = true
//                viewModel.uploadAudio(file, context, key = apiKey) { result ->
//                    transcribedText = result
//                    isUploading = false
//                }
                viewModel.getTranscribedText(
                  file=file
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_mic_none_24),
            contentDescription = "Mic",
            modifier = Modifier
                .size(100.dp)
                .pointerInput(Unit){
                    detectTapGestures(
                        onPress = {
                            launcher.launch(recordAudioPermission)
                            startRecording()
                            tryAwaitRelease()
                            stopRecordingAndUpload()
                        }
                    )
                },
            tint = if (isRecording) Color.Red else Color.Blue
        )

        Spacer(modifier = Modifier.height(20.dp))

//        if (isUploading) {
//            Text(text = "Uploading & transcribing...", color = Color.Blue)
//        } else {
//            transcribedText.value.success?.let {
//                Text(text =it,
//                    textAlign = TextAlign.Center
//                )
//            }
//        }

        if (transcribeUiState.isLoading){
            Text(text="uploading")
        }else{
            transcribeUiState.success?.let { Text(text=it) }
        }
    }
}

//@Composable
//fun TranscriptionKeyboardUI(
//    onMicClick: (File) -> Unit,
////    transcribedText: String?
//) {
//    val context = LocalContext.current
//    var isRecording by remember { mutableStateOf(false) }
//    var recorder: MediaRecorder? by remember { mutableStateOf(null) }
//    var audioFile: File? by remember { mutableStateOf(null) }
//
//    val viewmodel:TranscriptionViewModel= hiltViewModel()
//    val transcribedText=viewmodel.uiState.collectAsStateWithLifecycle()
//
//    fun startRecording() {
//        audioFile = File(context.cacheDir, "keyboard_recording_${System.currentTimeMillis()}.m4a")
//        recorder = MediaRecorder().apply {
//            setAudioSource(MediaRecorder.AudioSource.MIC)
//            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
//            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
//            setOutputFile(audioFile!!.absolutePath)
//            prepare()
//            start()
//        }
//        isRecording = true
//    }
//
//    fun stopRecording() {
//        recorder?.apply {
//            stop()
//            release()
//        }
//        recorder = null
//        isRecording = false
//        audioFile?.let { onMicClick(it) }
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.DarkGray)
//            .padding(8.dp),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        IconButton(onClick = {
//            if (!isRecording) startRecording() else stopRecording()
//        }) {
//            Icon(
//                painter = painterResource(id = R.drawable.baseline_mic_none_24),
//                contentDescription = "Mic",
//                tint = if (isRecording) Color.Red else Color.White,
//                modifier = Modifier.size(40.dp)
//            )
//        }
//
//        Text(
//            text = transcribedText.value.success ?: "Tap mic & speak",
//            color = Color.White
//        )
//    }
//}
