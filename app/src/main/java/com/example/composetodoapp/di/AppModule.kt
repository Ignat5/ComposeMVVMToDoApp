package com.example.composetodoapp.di

import android.app.Application
import androidx.room.Room
import com.example.composetodoapp.data.repository.ToDoRepository
import com.example.composetodoapp.data.repository.ToDoRepositoryImpl
import com.example.composetodoapp.data.room.ToDoDatabase
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
    fun provideToDoDB(app: Application): ToDoDatabase =
        Room.databaseBuilder(
            app,
            ToDoDatabase::class.java,
            "db_v1"
        ).build()

    @Provides
    @Singleton
    fun provideToDoRepository(db: ToDoDatabase): ToDoRepository =
        ToDoRepositoryImpl(db.todoDao)

}