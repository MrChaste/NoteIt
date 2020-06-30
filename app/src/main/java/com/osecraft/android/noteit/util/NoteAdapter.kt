package com.osecraft.android.noteit.util


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.osecraft.android.noteit.databinding.NoteItemBinding
import com.osecraft.android.noteit.db.Note

class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteHolder>(NoteDiffCallback()) {

    //Initialized to avoid null exception
    //private var notes: List<Note> = ArrayList()
    private lateinit var listener: OnItemClickedListener

     class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.priority == newItem.priority
        }
    }

    inner class NoteHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.textViewTitle
        val desc: TextView = binding.textViewDesc
        val priority: TextView = binding.textViewPriority
        private val parent: CardView = binding.parentLayout

        init {
            parent.setOnClickListener {
                val position = adapterPosition
                //Avoid invalid position error
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(getItem(position))
                }
            }
        }
    }

    //click listener for notes in for main activity
    interface OnItemClickedListener {
        fun onItemClicked(note: Note)
    }

    //forwards note object when clicked to whatever implements the interface above
    fun setOnItemClickListener(listener: OnItemClickedListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(
            NoteItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    //override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = getItem(position)
        holder.title.text = currentNote.title
        holder.desc.text = currentNote.description
        holder.priority.text = currentNote.priority.toString()
    }

    /**
    //Pass observed list of notes into recycler view
    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }
    */

    //Gets note position for particular note deleted using swipe by itemTouchHelper in Main Activity
    fun getNoteAt(position: Int): Note = getItem(position)

}