package com.example.notesmvvm.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmvvm.R
import com.example.notesmvvm.data.model.note.CreateNote
import com.example.notesmvvm.data.model.note.Note
import com.example.notesmvvm.data.remote.source.note.NoteRemoteDataSource
import com.example.notesmvvm.databinding.ActivityMainBinding
import com.example.notesmvvm.ui.adapter.NoteAdapter

class NotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var noteList: ArrayList<Note> = ArrayList()
    private lateinit var noteAdapter: NoteAdapter
    lateinit var rvNotes: RecyclerView
    private lateinit var note: CreateNote

    private var noteAPI = NoteRemoteDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteAdapter = NoteAdapter(noteList, this)

        val userID = intent.getIntExtra("user_id", 0)

        initRecycler(noteAdapter)
        noteAPI.getUserNotes(userID, noteAdapter)

        clickAddBtn()
    }

    private fun initRecycler(noteAdapter: NoteAdapter)
    {
        rvNotes = findViewById(R.id.recycler_view)
        rvNotes.adapter = noteAdapter
        rvNotes.layoutManager = LinearLayoutManager(this)
    }

    private fun clickAddBtn()
    {
        val userID = intent.getIntExtra("user_id", 0)

        binding.btnAdd.setOnClickListener {
            if (binding.etTitle.text.isEmpty() or binding.etContent.text.isEmpty())
            {
                Toast.makeText(this, "Please fill the boxes", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            note = CreateNote(
                userID,
                binding.etTitle.text.toString(),
                binding.etContent.text.toString()
            )

            noteAPI.createNote(note, userID, noteAdapter, this)

            binding.etTitle.text.clear()
            binding.etContent.text.clear()
        }
    }
}