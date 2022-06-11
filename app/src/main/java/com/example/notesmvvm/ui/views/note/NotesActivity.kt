package com.example.notesmvvm.ui.views.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmvvm.R
import com.example.notesmvvm.databinding.ActivityMainBinding
import com.example.notesmvvm.ui.adapter.NoteAdapter
import com.example.notesmvvm.ui.viewmodel.NotesActivityViewModel

class NotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerAdapter: NoteAdapter
    private lateinit var rvNotes: RecyclerView
    private lateinit var viewModel: NotesActivityViewModel
    private var userID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerAdapter = NoteAdapter(this, this, this)

        //Gets the user id from LoginActivity when user logged in successfully
        //The user id is also provided by CreateNoteActivity and UpdateActivity
        userID = intent.getIntExtra("user_id", 0)

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

    private fun clickAddBtn()
    {
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, CreateNoteActivity::class.java)
            intent.putExtra("user_id", userID)
            startActivity(intent)
        }
    }
}