package com.osecraft.android.noteit.repository

import androidx.lifecycle.LiveData
import com.osecraft.android.noteit.db.Note
import com.osecraft.android.noteit.db.NoteDao
import com.osecraft.android.noteit.util.Coroutine
import javax.inject.Inject

//Constructor injection for Hilt
class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

   private val allAvailableNotes: LiveData<List<Note>> = noteDao.getAllAvailableNotes()

    fun insertNote(note: Note) {
        Coroutine.io {
            noteDao.insertANote(note)
        }
    }
    fun updateNote(note: Note) {
        Coroutine.io {
            noteDao.updateANote(note)
        }
    }
    fun deleteNote(note: Note) {
        Coroutine.io {
            noteDao.deleteANote(note)
        }
    }
    fun deleteAllNotes() {
        Coroutine.io {
            noteDao.deleteAllNotes()
        }
    }

    fun getAllNotes(): LiveData<List<Note>> = allAvailableNotes
}