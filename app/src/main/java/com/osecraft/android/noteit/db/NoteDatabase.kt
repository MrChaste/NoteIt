package com.osecraft.android.noteit.db

import androidx.room.Database

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase {
}