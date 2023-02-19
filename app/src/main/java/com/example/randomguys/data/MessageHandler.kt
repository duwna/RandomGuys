package com.example.randomguys.data

import android.content.Context
import androidx.annotation.StringRes
import com.example.randomguys.presentation.utils.mutableEventFlow
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class MessageHandler {

    private val messageFlow = mutableEventFlow<MessageEvent>()

    fun observeMessages(): Flow<MessageEvent> = messageFlow

    fun showMessage(errorEvent: MessageEvent) {
        messageFlow.tryEmit(errorEvent)
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

fun exceptionHandler(messageHandler: MessageHandler) = CoroutineExceptionHandler { _, throwable ->
    Timber.tag("CoroutineExceptionHandler").e(throwable)
    messageHandler.showMessage(MessageEvent.Error(throwable))
}
