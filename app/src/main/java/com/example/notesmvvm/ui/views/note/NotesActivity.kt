package com.example.notesmvvm.ui.views.note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmvvm.R
import com.example.notesmvvm.data.remote.model.note.CreateNote
import com.example.notesmvvm.databinding.ActivityMainBinding
import com.example.notesmvvm.ui.adapter.NoteAdapter
import com.example.notesmvvm.ui.viewmodel.NotesActivityViewModel

class NotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var recyclerAdapter: NoteAdapter
    lateinit var rvNotes: RecyclerView
    private lateinit var note: CreateNote
    private lateinit var viewModel: NotesActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerAdapter = NoteAdapter(this, this, this)

        //Gets the user id from LoginActivity when user logged in successfully
        val userID = intent.getIntExtra("user_id", 0)

        initRecycler(recyclerAdapter)
        initViewModel(userID)
        createNoteViewModel()
        clickAddBtn()
    }

    private fun initRecycler(recyclerAdapter: NoteAdapter)
    {
        rvNotes = findViewById(R.id.recycler_view)
        rvNotes.adapter = recyclerAdapter
        rvNotes.layoutManager = LinearLayoutManager(this)
    }

    private fun initViewModel(userID: Int)
    {
        viewModel = ViewModelProvider(this)[NotesActivityViewModel::class.java]
        viewModel.getUserNotes(userID)

        viewModel.getNoteLiveData().observe(this) {
            if (it == null)
            {
                Toast.makeText(this, "Error in getting data", Toast.LENGTH_LONG).show()
                return@observe
            }

            recyclerAdapter.setData(it)
        }
    }

    private fun createNoteViewModel()
    {
        val noteViewModel = ViewModelProvider(this)[NotesActivityViewModel::class.java]

        noteViewModel.getCreateNoteLiveData().observe(this)
        {
            if (it == null)
            {
                Toast.makeText(this, "Failed to create note", Toast.LENGTH_LONG).show()
                return@observe
            }

            noteViewModel.getUserNotes(it.data.userID)
            Toast.makeText(this, "New note created", Toast.LENGTH_LONG).show()
        }
    }

    private fun clickAddBtn()
    {
        binding.btnAdd.setOnClickListener {
            createNote()
        }
    }

    private fun createNote()
    {
        val userID = intent.getIntExtra("user_id", 0)

        if (binding.etTitle.text.isEmpty() or binding.etContent.text.isEmpty())
        {
            Toast.makeText(this, "Please fill the boxes", Toast.LENGTH_LONG).show()
            return
        }

        note = CreateNote(
            userID,
            binding.etTitle.text.toString(),
            binding.etContent.text.toString()
        )

        viewModel.addNote(note)

        binding.etTitle.text.clear()
        binding.etContent.text.clear()
    }
}