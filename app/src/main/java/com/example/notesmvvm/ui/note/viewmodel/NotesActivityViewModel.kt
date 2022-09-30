package com.example.notesmvvm.ui.note.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesmvvm.data.model.note.*
import com.example.notesmvvm.data.remote.source.note.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesActivityViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private var recyclerListLiveData: MutableLiveData<ArrayList<NoteResponse>> = MutableLiveData()
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
    fun getNoteLiveData(): MutableLiveData<ArrayList<NoteResponse>>
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

    fun getUserNotes(userID: Int, context: Context)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val response = noteRepository.getUserNotes(userID, context)
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