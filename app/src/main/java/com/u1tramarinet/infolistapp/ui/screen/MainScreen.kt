package com.u1tramarinet.infolistapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.u1tramarinet.infolistapp.ui.InfoListScreen

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigateClick: (InfoListScreen) -> Unit,
) {
    Column(
        modifier = modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        InfoListScreen.entries
            .filter { entry -> entry.needListUp }
            .forEach { screen ->
                Button(onClick = { onNavigateClick(screen) }) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = screen.title),
                    )
                }
            }
    }
}