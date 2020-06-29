package com.osecraft.android.noteit.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.osecraft.android.noteit.R
import com.osecraft.android.noteit.databinding.ActivityMainBinding
import com.osecraft.android.noteit.db.Note
import com.osecraft.android.noteit.util.NoteAdapter
import com.osecraft.android.noteit.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NoteAdapter.OnItemClickedListener {
    companion object {
        const val ADD_NOTE_REQUEST_CODE = 1
    }
    private lateinit var binding: ActivityMainBinding
    private val viewModel: NoteViewModel by viewModels()
    private val adapter = NoteAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
        //Calls the addEditNoteActivity and expects a result
        binding.btnAddNote.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST_CODE)
        }
        viewModel.getAllNotes().observe(this, Observer {
            //Updates list in adapter with the observed list from view-model
            adapter.setNotes(it)
        })

        //Swipe left or right to delete functionality
        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                    Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        adapter.setOnItemClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val desc = data?.getStringExtra(AddEditNoteActivity.EXTRA_DESC)
            val priority = data?.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)
            val note = Note(title!!, desc!!, priority!!)
            viewModel.insert(note)
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        }  else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, "Note cannot be updated", Toast.LENGTH_SHORT).show()
                return
            }
            val title = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val desc = data?.getStringExtra(AddEditNoteActivity.EXTRA_DESC)
            val priority = data?.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)
            val note = Note(title!!, desc!!, priority!!)
            note.id = id!!
            viewModel.update(note)
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.delete_all_notes -> {
                viewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
                true
            } else -> super.onOptionsItemSelected(item)
        }


    }

    override fun onItemClicked(note: Note) {
        val intent = Intent(this, AddEditNoteActivity::class.java)
        intent.apply {
            putExtra(AddEditNoteActivity.EXTRA_ID, note.id)
            putExtra(AddEditNoteActivity.EXTRA_TITLE, note.title)
            putExtra(AddEditNoteActivity.EXTRA_DESC, note.description)
            putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.priority)
            startActivityForResult(intent, 2)
        }
    }
}