package com.notes.di

import android.content.Context
import androidx.room.Room
import com.notes.database.AppDatabase
import com.notes.database.dao.NotesDao
import com.notes.database.repository.NotesRepository
import com.notes.database.viewmodel.NotesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

        @Singleton
        @Provides
        fun providesDatabase(@ApplicationContext appContext: Context): AppDatabase {
            return Room.databaseBuilder(
                appContext, AppDatabase::class.java, AppDatabase.DATABASE
            )
                .fallbackToDestructiveMigration()
                .build()
        }

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }
    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @Provides
    @Singleton
    fun providesNotesDao(database: AppDatabase) = database.notesDao()

    @Provides
    @Singleton
    fun providesNotesRepository(dao: NotesDao) = NotesRepository(dao)

    @Provides
    @Singleton
    fun providesNotesViewModel(notesRepository: NotesRepository) = NotesViewModel(notesRepository)
}