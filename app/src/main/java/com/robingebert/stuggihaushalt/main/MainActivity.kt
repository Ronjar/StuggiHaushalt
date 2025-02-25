package com.robingebert.stuggihaushalt.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.robingebert.paraparia.main.ObserveAsEvents
import com.robingebert.stuggihaushalt.navigation.AppNavigation
import com.robingebert.stuggihaushalt.navigation.Screen
import com.robingebert.stuggihaushalt.ui.theme.StuggiHaushaltTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StuggiHaushaltTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                val scope = rememberCoroutineScope()
                ObserveAsEvents(
                    flow = viewModel.snackbarController.events,
                    snackbarHostState
                ) { event ->
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()

                        val result = snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action?.name,
                            duration = event.duration
                        )

                        if(result == SnackbarResult.ActionPerformed) {
                            event.action?.action?.invoke()
                        }
                    }
                }
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    modifier = Modifier.fillMaxSize().imePadding(),
                    topBar = {
                        TopAppBar(
                            title = { Text(navController.currentScreenTitle()) },
                            actions = {
                                if (navController.currentScreenTitle() != Screen.Settings.title) {
                                    IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                                        Icon(
                                            imageVector = Icons.Rounded.Settings,
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                            /*navigationIcon = {
                                if (navController.currentScreenTitle() != Screen.Main.title) {
                                    IconButton(onClick = { navController.navigateUp() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }*/
                        )
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .consumeWindowInsets(paddingValues)
                    ) {
                        AppNavigation(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
private fun NavController.currentScreenTitle(): String {
    val currentRoute = currentBackStackEntryAsState().value?.destination?.route
    return when (currentRoute) {
        Screen.Main.route -> Screen.Main.title
        Screen.Login.route -> Screen.Login.title
        Screen.Settings.route -> Screen.Settings.title
        else -> "StuggiHaushalt"
    }
}