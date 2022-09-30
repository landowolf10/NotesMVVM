package com.example.notesmvvm.ui.note.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notesmvvm.data.model.note.CreateNoteRequest
import com.example.notesmvvm.databinding.ActivityCreateNoteBinding
import com.example.notesmvvm.ui.note.viewmodel.NotesActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNoteBinding
    private lateinit var note: CreateNoteRequest
    private lateinit var viewModel: NotesActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNoteViewModel()
        clickAddBtn()
    }

    private fun createNoteViewModel()
    {
        viewModel = ViewModelProvider(this)[NotesActivityViewModel::class.java]

        viewModel.getCreateNoteLiveData().observe(this)
        {
            if (it == null)
            {
                Toast.makeText(this, "Failed to create note", Toast.LENGTH_LONG).show()
                return@observe
            }

            viewModel.getUserNotes(it.data.userID, this)
            Toast.makeText(this, "New note created", Toast.LENGTH_LONG).show()
        }
    }

    private fun clickAddBtn()
    {
        binding.btnCreateNote.setOnClickListener {
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

        note = CreateNoteRequest(
            userID,
            binding.etTitle.text.toString(),
            binding.etContent.text.toString()
        )

        viewModel.addNote(note)

        binding.etTitle.text.clear()
        binding.etContent.text.clear()

        val intent = Intent(this, NotesActivity::class.java)
        intent.putExtra("user_id", userID)
        startActivity(intent)
    }
}