package com.example.randomguys.presentation

import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.randomguys.data.MessageEvent
import com.example.randomguys.data.MessageHandler
import com.example.randomguys.presentation.utils.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SnackbarScaffold(
    messageHandler: MessageHandler,
    content: @Composable () -> Unit
) {

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

                val showJob = launch {
                    snackbarColor = if (event is MessageEvent.Error) errorSnackbarColor else defaultSnackbarColor
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.text(appContext),
                        duration = SnackbarDuration.Indefinite
                    )
                }

                launch {
                    delay(event.durationMillis)
                    showJob.cancel()
                }
            }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { host ->
            SnackbarHost(host) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = snackbarColor,
                    modifier = Modifier.noRippleClickable {
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    }
                )
            }
        }
    ) {
        content()
        it.toString()
    }
}
