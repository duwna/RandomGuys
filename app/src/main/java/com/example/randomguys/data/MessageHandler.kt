package com.example.randomguys.data

import android.content.Context
import androidx.annotation.StringRes
import com.example.randomguys.presentation.utils.mutableEventFlow
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MessageHandler {

    private val messageFlow = mutableEventFlow<MessageEvent>()

    fun observeMessages(): Flow<MessageEvent> = messageFlow

    fun showMessage(errorEvent: MessageEvent) {
        messageFlow.tryEmit(errorEvent)

        if (errorEvent is MessageEvent.Error) {
            Timber.tag("MessageHandler").e(errorEvent.throwable)
        }
    }

}

sealed class MessageEvent(val durationMillis: Long = DURATION) {
    companion object {
        private const val DURATION = 1000L
    }

    abstract fun text(context: Context): String

    class Message(val message: String, durationMillis: Long = DURATION) : MessageEvent(durationMillis) {
        override fun text(context: Context): String = message
    }

    class Error(val throwable: Throwable, durationMillis: Long = DURATION) : MessageEvent(durationMillis) {
        override fun text(context: Context): String = throwable.message.orEmpty()
    }

    class Id(@StringRes val messageId: Int, durationMillis: Long = DURATION) : MessageEvent(durationMillis) {
        override fun text(context: Context): String = context.getString(messageId)
    }

    class WithArgs(
        @StringRes val messageId: Int,
        durationMillis: Long = DURATION,
        private vararg val args: Any
    ) : MessageEvent(durationMillis) {
        override fun text(context: Context): String = context.getString(messageId, args)
    }
}

fun CoroutineScope.launchHandlingErrors(
    errorHandler: MessageHandler,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    val exceptionHandler = messageHandler(errorHandler)
    launch(context = coroutineContext + exceptionHandler, block = block)
}

fun messageHandler(messageHandler: MessageHandler) = CoroutineExceptionHandler { _, throwable ->
    messageHandler.showMessage(MessageEvent.Error(throwable))
}
