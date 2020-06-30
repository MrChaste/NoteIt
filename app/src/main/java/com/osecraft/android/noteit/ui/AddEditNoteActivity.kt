package com.osecraft.android.noteit.ui

import android.app.Activity
import android.content.Intent
import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.osecraft.android.noteit.R
import com.osecraft.android.noteit.databinding.ActivityAddNoteBinding

class AddEditNoteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.osecraft.android.noteit.ui.EXTRA_ID"
        const val EXTRA_TITLE = "com.osecraft.android.noteit.ui.EXTRA_TITLE"
        const val EXTRA_DESC = "com.osecraft.android.noteit.ui.EXTRA_DESC"
        const val EXTRA_PRIORITY = "com.osecraft.android.noteit.ui.EXTRA_PRIORITY"
    }
    private lateinit var binding: ActivityAddNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.numPicker.minValue = 1
        binding.numPicker.maxValue = 10

        //Adds the close icon to the action_bar
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        //get data in intent sent from main activity
        val intent = intent
        if (intent.hasExtra(EXTRA_ID)) {
            //if the intent has the id parameter, change title of appbar to Edit note
            title = "Edit note"
            binding.titleEdit.setText(intent.getStringExtra(EXTRA_TITLE))
            binding.desc.setText(intent.getStringExtra(EXTRA_DESC))
            binding.numPicker.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
        } else {
            title = "Add Note"
        }
    }

    //Function is called when save note is clicked in toolbar
    private fun saveNote() {
        val title = binding.titleEdit.text.toString()
        val desc = binding.desc.text.toString()
        val priority = binding.numPicker.value //1 - 10
        //Avoid input if edit-text is empty
        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(this, "Insert a title or description", Toast.LENGTH_LONG)
                .show()
            return
        }
        //Sends data from this activity to the calling activity
        val intent = Intent()
        intent.apply {
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_DESC, desc)
            putExtra(EXTRA_PRIORITY, priority)
            val id = getIntent().getIntExtra(EXTRA_ID, -1)
            if (id != -1) {
                intent.putExtra(EXTRA_ID, id)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    //Create menu in action_bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    //Respond to user click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            } else -> super.onOptionsItemSelected(item)
        }

    }
}