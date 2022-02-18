package com.example.notesmvvm.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmvvm.R
import com.example.notesmvvm.data.model.note.Note
import com.example.notesmvvm.data.remote.source.note.NoteRemoteDataSource
import com.example.notesmvvm.databinding.ActivityMainBinding
import com.example.notesmvvm.databinding.CardViewBinding
import com.example.notesmvvm.ui.views.UpdateNoteActivity

class NoteAdapter(note: MutableList<Note>, context: Context): RecyclerView.Adapter<NoteAdapter.ViewHolder>()
{
    private lateinit var binding: ActivityMainBinding
    var noteList = note
    var noteContext = context

    fun setData(note: MutableList<Note>)
    {
        this.noteList = note
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.card_view,
            parent,
            false
        )

        binding = ActivityMainBinding.inflate(LayoutInflater.from(
            parent.context
        ),
            parent,
            false
        )

        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(noteList[position])
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        private val binding = CardViewBinding.bind(view)

        fun render(item: Note)
        {
            binding.txtTitle.text = item.title
            binding.txtContent.text = item.content

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, UpdateNoteActivity::class.java)
                intent.putExtra("user_id", item.userID)
                intent.putExtra("update_note_id", item.id)
                intent.putExtra("note_title", item.title)
                intent.putExtra("note_content", item.content)
                itemView.context.startActivity(intent)
            }

            binding.btnDelete.setOnClickListener {
                val noteAPI = NoteRemoteDataSource()
                val noteID: Int = item.id
                val itemPosition = adapterPosition

                noteList.removeAt(itemPosition)
                noteAPI.deleteNote(noteID, noteContext)
                notifyItemRemoved(itemPosition)
            }
        }
    }
}