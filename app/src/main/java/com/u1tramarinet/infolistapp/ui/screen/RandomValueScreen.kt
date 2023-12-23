package com.u1tramarinet.infolistapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.u1tramarinet.infolistapp.ui.common.PlayPauseButton

@Composable
fun RandomValueScreen(
    modifier: Modifier,
    viewModel: RandomValueViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsState()
    val value = state.value
    if (value !is ScreenState.Success) {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "読み込み中")
        }
        return
    }
    val onChanged: (Long) -> Unit = { t -> viewModel.updateInterval(t) }
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val maxLen = value.data.intervalConfig.slowest.toString().length
        Text(
            text = value.data.randomValue.toString(),
            fontSize = 64.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            UpdateIntervalButton(
                title = "低速\n" + padStartLong(value.data.intervalConfig.slowest, maxLen) + "ms",
                intervalTimeMills = value.data.intervalConfig.slowest,
                currentIntervalTimeMills = value.data.interval,
                onClick = onChanged,
            )
            UpdateIntervalButton(
                title = "中速\n" + padStartLong(value.data.intervalConfig.middle, maxLen) + "ms",
                intervalTimeMills = value.data.intervalConfig.middle,
                currentIntervalTimeMills = value.data.interval,
                onClick = onChanged,
            )
            UpdateIntervalButton(
                title = "高速\n" + padStartLong(value.data.intervalConfig.fastest, maxLen) + "ms",
                intervalTimeMills = value.data.intervalConfig.fastest,
                currentIntervalTimeMills = value.data.interval,
                onClick = onChanged,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        UpdateIntervalSlider(
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { f -> viewModel.updateIntervalViaSlider(f) },
            sliderState = value.data.sliderState,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Interval: ${padStartLong(value.data.interval, maxLen)} ms",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        PlayPauseButton(
            modifier = Modifier.fillMaxWidth(),
            isPlaying = value.data.isRunning,
            onClick = { nextState -> viewModel.updateRunningState(nextState) })
    }
}

@Composable
private fun UpdateIntervalButton(
    modifier: Modifier = Modifier,
    title: String,
    intervalTimeMills: Long,
    currentIntervalTimeMills: Long,
    onClick: (Long) -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = { onClick(intervalTimeMills) },
        enabled = (intervalTimeMills != currentIntervalTimeMills),
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun UpdateIntervalSlider(
    modifier: Modifier = Modifier,
    sliderState: SliderState,
    onValueChange: (Float) -> Unit,
) {
    Slider(
        modifier = modifier,
        valueRange = sliderState.minValue..sliderState.maxValue,
        steps = sliderState.steps,
        value = sliderState.value,
        onValueChange = { f -> onValueChange(f) },
    )
}

private fun padStartLong(value: Long, length: Int): String {
    return value.toString().padStart(length, ' ')
}