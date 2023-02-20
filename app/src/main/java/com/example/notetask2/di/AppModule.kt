package com.example.database1.di

import android.app.Application
import androidx.room.Room
import com.example.notetask2.model.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app:Application): NoteDatabase {

        val database =Room.databaseBuilder(app,NoteDatabase::class.java,"contact_dp").build()
        return database
    }

}