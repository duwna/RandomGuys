package com.example.randomguys.di

import android.content.Context
import android.os.Vibrator
import androidx.core.content.getSystemService
import com.example.randomguys.data.PersistentStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providePersistentStorage(@ApplicationContext context: Context) =
        PersistentStorage(context)

    @Provides
    fun provideVibrator(@ApplicationContext context: Context): Vibrator =
        requireNotNull(context.getSystemService())
}
