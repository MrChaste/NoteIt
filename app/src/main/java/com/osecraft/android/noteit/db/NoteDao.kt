package com.osecraft.android.noteit.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertANote(note: Note)

    @Update
    suspend fun updateANote(note: Note)

    @Delete
    suspend fun deleteANote(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllAvailableNotes() : LiveData<List<Note>>
}