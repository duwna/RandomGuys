package com.example.randomguys.presentation

import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.randomguys.data.MessageEvent
import com.example.randomguys.data.MessageHandler
import kotlinx.coroutines.launch

@Composable
fun SnackbarScaffold(messageHandler: MessageHandler) {

    val scaffoldState = rememberScaffoldState()
    val appContext = LocalContext.current.applicationContext

    val defaultSnackbarColor = MaterialTheme.colorScheme.primary
    val errorSnackbarColor = MaterialTheme.colorScheme.error
    var snackbarColor by remember { mutableStateOf(defaultSnackbarColor) }

    LaunchedEffect(Unit) {
        messageHandler
            .observeMessages()
            .collect { event ->
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                launch {
                    snackbarColor = if (event is MessageEvent.Error) errorSnackbarColor else defaultSnackbarColor
                    scaffoldState.snackbarHostState.showSnackbar(message = event.text(appContext))
                }
            }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { host ->
            SnackbarHost(host) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = snackbarColor
                )
            }
        }
    ) {
        it.toString()
    }
}
