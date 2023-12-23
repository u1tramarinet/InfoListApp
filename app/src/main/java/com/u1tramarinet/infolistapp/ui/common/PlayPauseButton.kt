package com.u1tramarinet.infolistapp.ui.common

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PlayPauseButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isPlaying: Boolean,
    onClick: (nextState: Boolean) -> Unit
) {
    val title = if (isPlaying) "停止する" else "再生する"
    val foregroundColor = if (isPlaying) Color.White else Color.Black
    val backgroundColor = if (isPlaying) Color.Red else Color.Green
    Button(
        modifier = modifier,
        onClick = { onClick(!isPlaying) },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = foregroundColor,
        )
    ) {
        Text(text = title)
    }
}