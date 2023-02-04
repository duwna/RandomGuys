package com.example.randomguys.di

import android.content.Context
import android.os.Vibrator
import androidx.core.content.getSystemService
import com.example.randomguys.data.MessageHandler
import com.example.randomguys.data.PersistentStorage
import com.example.randomguys.navigation.Navigator
import com.example.randomguys.navigation.NavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePersistentStorage(@ApplicationContext context: Context) =
        PersistentStorage(context)

    @Provides
    fun provideVibrator(@ApplicationContext context: Context): Vibrator =
        requireNotNull(context.getSystemService())

    @Provides
    @Singleton
    fun provideMessageHandler() = MessageHandler()

    @Provides
    @Singleton
    fun provideNavigator(): Navigator = NavigatorImpl()
}
