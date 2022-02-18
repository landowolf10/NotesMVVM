package com.example.notesmvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesmvvm.data.model.note.Note

class NotesActivityViewModel: ViewModel() {
    lateinit var recyclerListLiveData: MutableLiveData<Note>

    init {
        recyclerListLiveData = MutableLiveData()
    }

    fun getRecyclerListObserver(): MutableLiveData<Note>
    {
        return  recyclerListLiveData
    }
}