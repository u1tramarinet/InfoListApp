package com.u1tramarinet.infolistapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.u1tramarinet.infolistapp.R
import com.u1tramarinet.infolistapp.ui.screen.MainScreen
import com.u1tramarinet.infolistapp.ui.screen.RandomValueScreen
import com.u1tramarinet.infolistapp.ui.screen.RandomValuesScreen

@Composable
fun InfoListApp(
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val initialScreen = InfoListScreen.Top
    val currentScreen = InfoListScreen.valueOf(
        backStackEntry?.destination?.route ?: initialScreen.name
    )

    Scaffold(
        topBar = {
            InfoListAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = initialScreen.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(InfoListScreen.Top.name) {
                MainScreen(Modifier.fillMaxSize(), onNavigateClick = { screen ->
                    navController.navigate(screen.name)
                })
            }
            composable(InfoListScreen.RandomValue.name) {
                RandomValueScreen(modifier = Modifier.fillMaxSize())
            }
            composable(InfoListScreen.RandomValues.name) {
                RandomValuesScreen(modifier = Modifier.fillMaxSize())
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoListAppBar(
    currentScreen: InfoListScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = stringResource(id = currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button),
                    )
                }
            }
        }
    )
}

enum class InfoListScreen(@StringRes val title: Int, val needListUp: Boolean = false) {
    Top(title = R.string.app_name),
    RandomValue(title = R.string.title_random_value, needListUp = true),
    RandomValues(title = R.string.title_random_values, needListUp = true),
}