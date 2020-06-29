package com.osecraft.android.noteit.di.modules

import com.osecraft.android.noteit.db.NoteDao
import com.osecraft.android.noteit.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NoteRepositoryModule {
    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository = NoteRepository(noteDao)
}