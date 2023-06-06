package gpt.voice.assistant


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import gpt.voice.assistant.viewmodel.SpeechViewModel


private const val TAG = "SpeechToTextScreen"

@Composable
fun SpeechToTextScreen(onButtonClick: () -> Unit, viewModel: SpeechViewModel) {
    SearchButton(onButtonClick = onButtonClick)
}


@Composable
fun SearchButton(onButtonClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onButtonClick,
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        ) {
            Icon(
                Icons.Filled.Search, contentDescription = "Microphone", modifier = Modifier
                    .size(50.dp)
            )
        }
    }
}