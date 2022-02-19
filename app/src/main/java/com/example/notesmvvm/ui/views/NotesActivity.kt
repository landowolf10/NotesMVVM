package com.example.notesmvvm.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmvvm.R
import com.example.notesmvvm.data.model.note.CreateNote
import com.example.notesmvvm.data.model.note.Note
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

        recyclerAdapter = NoteAdapter(this, this)

        val userID = intent.getIntExtra("user_id", 0)

        initRecycler(recyclerAdapter)
        initViewModel(userID)
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
        viewModel.getRecyclerListObserver().observe(this) {
            if (it != null)
                recyclerAdapter.setData(it)
            else
                Toast.makeText(this, "Error in getting data", Toast.LENGTH_LONG).show()
        }

        viewModel.getUserNotes(userID)
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

            viewModel.addNote(note, this)

            binding.etTitle.text.clear()
            binding.etContent.text.clear()
        }
    }
}