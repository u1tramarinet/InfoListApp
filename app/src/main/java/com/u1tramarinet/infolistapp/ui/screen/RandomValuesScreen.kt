package com.u1tramarinet.infolistapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.u1tramarinet.infolistapp.ui.common.PlayPauseButton

@Composable
fun RandomValuesScreen(
    modifier: Modifier,
    viewModel: RandomValuesViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(20.dp)
                )
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        enabled = true,
                        state = rememberScrollState(),
                    )
            ) {
                uiState.value.randomValues.forEach { value ->
                    RandomValueStateItem(state = value)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PlayPauseButton(
                modifier = Modifier.weight(1f),
                enabled = uiState.value.isRunnable,
                isPlaying = uiState.value.isRunning,
                onClick = { nextState -> viewModel.changeRunningState(nextState) },
            )
            Button(
                modifier = Modifier.weight(1f),
                onClick = { viewModel.addRandomValue() },
            ) {
                Text(text = "追加")
            }
        }
    }
}

@Composable
fun RandomValueStateItem(state: RandomValueState) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = state.id.toString(), fontSize = 32.sp)
        Spacer(modifier = Modifier.weight(1f))
        if (state !is RandomValueState.Success) {
            Text(
                text = "待機中",
                fontSize = 32.sp,
            )
        }
        Text(
            text = if (state is RandomValueState.Success) state.value.toString() else "",
            fontSize = 56.sp,
        )
        Spacer(modifier = Modifier.width(8.dp))
    }
}