package com.example.randomguys.data

import android.content.Context
import androidx.annotation.StringRes
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MessageHandler {

    private val messageFlow = MutableSharedFlow<MessageEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun observeMessages(): Flow<MessageEvent> = messageFlow

    fun showError(errorEvent: MessageEvent) {
        messageFlow.tryEmit(errorEvent)

        if (errorEvent is MessageEvent.Error) {
            Timber.tag("MessageHandler").e(errorEvent.throwable)
        }
    }

}

sealed class MessageEvent {

    abstract fun text(context: Context): String

    class Message(val message: String) : MessageEvent() {
        override fun text(context: Context): String = message
    }

    class Error(val throwable: Throwable) : MessageEvent() {
        override fun text(context: Context): String = throwable.message.orEmpty()
    }

    class Id(@StringRes val messageId: Int) : MessageEvent() {
        override fun text(context: Context): String = context.getString(messageId)
    }

    class WithArgs(@StringRes val messageId: Int, private vararg val args: Any) : MessageEvent() {
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
    messageHandler.showError(MessageEvent.Error(throwable))
}
