package gpt.voice.assistant

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import gpt.voice.assistant.ui.theme.GPTVOICEASSISTANTTheme
import gpt.voice.assistant.viewmodel.SpeechViewModel

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private val speechViewModel: SpeechViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GPTVOICEASSISTANTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    setContent {
                        GPTVOICEASSISTANTTheme {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colors.background
                            ) {
                                InitApp(viewModel = speechViewModel)
                            }
                        }
                    }
                }
            }
        }
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (!isGranted) {
                    Toast.makeText(
                        this,
                        "Sorry, we cannot record audio without the permission :(",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    @Composable
    fun InitApp(viewModel: SpeechViewModel) {
        SpeechToTextScreen(
            onButtonClick = { speechToText() },
            viewModel = viewModel
        )
    }

    private fun speechToText() {
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        val recognitionListener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.d(TAG, "onReadyForSpeech() called")
            }

            override fun onBeginningOfSpeech() {
                Log.d(TAG, "onBeginningOfSpeech() called")
            }

            override fun onEndOfSpeech() {
                Log.d(TAG, "onEndOfSpeech() called")
            }

            override fun onResults(results: Bundle?) {
                Log.d(TAG, "onResults() called")
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val recognizedText = matches[0] // Get the first recognized text
                    Log.d(TAG, "Recognized text: $recognizedText")

                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                Log.d(TAG, "onPartialResults() called")
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.d(TAG, "onEvent() called")
            }

            override fun onError(error: Int) {
                Log.d(TAG, "onError() called")
            }

            override fun onRmsChanged(rmsdB: Float) {
                Log.d(TAG, "onRmsChanged() called")
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                Log.d(TAG, "onBufferReceived() called")
            }
        }
        speechRecognizer.setRecognitionListener(recognitionListener)
        speechRecognizer.startListening(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH))
    }
}