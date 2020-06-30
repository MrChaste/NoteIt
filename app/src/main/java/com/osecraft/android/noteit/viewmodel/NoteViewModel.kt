package com.osecraft.android.noteit.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.osecraft.android.noteit.db.Note
import com.osecraft.android.noteit.repository.NoteRepository

//Constructor injection for view-model in Hilt
class NoteViewModel @ViewModelInject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    private val allAvailableNotes: LiveData<List<Note>> = repository.getAllNotes()
    fun insert(note: Note) = repository.insertNote(note)
    fun update(note: Note) = repository.updateNote(note)
    fun delete(note: Note) = repository.deleteNote(note)
    fun deleteAllNotes() = repository.deleteAllNotes()
    fun getAllNotes(): LiveData<List<Note>> = allAvailableNotes
}