package com.example.notesmvvm.ui.views.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notesmvvm.data.remote.model.note.UpdateNote
import com.example.notesmvvm.databinding.ActivityUpdateNoteBinding
import com.example.notesmvvm.ui.viewmodel.NotesActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var viewModel: NotesActivityViewModel
    private var userID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        getNoteInfo()
        clickUpdateBtn()
    }

    private fun initViewModel()
    {
        viewModel = ViewModelProvider(this)[NotesActivityViewModel::class.java]

        viewModel.getUpdateNoteLiveData().observe(this) {
            if (it == null)
            {
                Toast.makeText(this, "Error in updating data", Toast.LENGTH_LONG).show()
                return@observe
            }

            Toast.makeText(this, "Note updated successfully!", Toast.LENGTH_LONG).show()
        }
    }

    /*Sets the note info on the text boxes when selecting one*/
    private fun getNoteInfo()
    {
        val noteTitle = intent.getStringExtra("note_title") //This comes from the adapter
        val noteContent = intent.getStringExtra("note_content") //This comes from the adapter
        binding.etUpdateTitle.setText(noteTitle)
        binding.etUpdateContent.setText(noteContent)
    }

    private fun clickUpdateBtn()
    {
        binding.btnUpdate.setOnClickListener {
            updateNote()
        }
    }

    private fun updateNote()
    {
        if (binding.etUpdateTitle.text.isEmpty() or binding.etUpdateContent.text.isEmpty())
        {
            Toast.makeText(this, "Please fill the boxes", Toast.LENGTH_LONG).show()
            return
        }

        userID = intent.getIntExtra("user_id", 0) //This comes from the adapter
        val noteID = intent.getIntExtra("update_note_id", 0) //This comes from the adapter

        val updatedData = UpdateNote(
            userID,
            noteID,
            binding.etUpdateTitle.text.toString(),
            binding.etUpdateContent.text.toString()
        )

        viewModel.updateNote(updatedData)
        val intent = Intent(this, NotesActivity::class.java)
        intent.putExtra("user_id", userID)
        startActivity(intent)
    }
}