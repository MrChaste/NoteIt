package com.osecraft.android.noteit.di.modules

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.osecraft.android.noteit.db.Note
import com.osecraft.android.noteit.db.NoteDao
import com.osecraft.android.noteit.db.NoteDatabase
import com.osecraft.android.noteit.util.Coroutine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    lateinit var database: NoteDatabase

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context) : NoteDatabase {
        database = Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_database"
        ).fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Coroutine.io {
                        database.noteDao().insertANote(Note("Title 1", "Description 1", 1))
                        database.noteDao().insertANote(Note("Title 2", "Description 2", 2))
                        database.noteDao().insertANote(Note("Title 3", "Description 3", 3))
                    }
                }
            })
            .build()
        return database
    }

    @Provides
    fun provideNoteDao(database: NoteDatabase) : NoteDao = database.noteDao()
}