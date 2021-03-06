package com.example.notesmvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesmvvm.data.remote.model.note.*
import com.example.notesmvvm.data.remote.source.note.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesActivityViewModel: ViewModel() {
    private var noteRepository = NoteRepository()
    private var recyclerListLiveData: MutableLiveData<ArrayList<Note>> = MutableLiveData()
    private var createNoteLiveData: MutableLiveData<CreateNoteResponse> = MutableLiveData()
    private var updateNoteLiveData: MutableLiveData<UpdateNoteResponse> = MutableLiveData()
    private var deleteNoteLiveData: MutableLiveData<DeleteNoteResponse> = MutableLiveData()

    init {
        recyclerListLiveData = noteRepository.getNoteLiveData()
        createNoteLiveData = noteRepository.getCreateNoteLiveData()
        updateNoteLiveData = noteRepository.getUpdateNoteLiveData()
        deleteNoteLiveData = noteRepository.getDeleteNoteLiveData()
    }

    /*Observable methods*/
    fun getNoteLiveData(): MutableLiveData<ArrayList<Note>>
    {
        return  recyclerListLiveData
    }

    fun getCreateNoteLiveData(): MutableLiveData<CreateNoteResponse>
    {
        return createNoteLiveData
    }

    fun getUpdateNoteLiveData(): MutableLiveData<UpdateNoteResponse>
    {
        return updateNoteLiveData
    }

    fun getDeleteNoteLiveData(): MutableLiveData<DeleteNoteResponse>
    {
        return deleteNoteLiveData
    }

    fun getUserNotes(userID: Int)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val response = noteRepository.getUserNotes(userID)
            recyclerListLiveData.postValue(response)
        }
    }

    fun addNote(note: CreateNoteRequest)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val response = noteRepository.addNote(note)
            createNoteLiveData.postValue(response)
        }
    }

    fun updateNote(updatedData: UpdateNote)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val response = noteRepository.updateNote(updatedData)
            updateNoteLiveData.postValue(response)
        }
    }

    fun deleteNote(noteID: Int)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val response = noteRepository.deleteNote(noteID)
            deleteNoteLiveData.postValue(response)
        }
    }
}