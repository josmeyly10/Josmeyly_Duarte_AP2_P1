package com.example.josmeyly_duarte_ap2_p1.di

import android.content.Context
import androidx.room.Room
import com.example.josmeyly_duarte_ap2_p1.data.local.database.BorrameDb
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
    fun provideEstudianteDb(
        @ApplicationContext appContext: Context
    ): BorrameDb = Room.databaseBuilder(
        appContext,
        BorrameDb::class.java,
        "Borrame.db"
    )
        .fallbackToDestructiveMigration()
        .build()
}