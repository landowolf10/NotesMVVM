package com.example.notesmvvm.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesmvvm.data.model.note.*
import com.example.notesmvvm.data.remote.net.NoteRemoteService
import com.example.notesmvvm.data.remote.source.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class NotesActivityViewModel: ViewModel() {
    private var recyclerListLiveData: MutableLiveData<ArrayList<Note>> = MutableLiveData()
    private var createNoteLiveData: MutableLiveData<NoteResponse> = MutableLiveData()
    private var updateNoteLiveData: MutableLiveData<UpdateNoteResponse> = MutableLiveData()
    private var deleteNoteLiveData: MutableLiveData<DeleteNoteResponse> = MutableLiveData()
    private val retroInstance: NoteRemoteService = RetrofitBuilder.getRetrofit().create(NoteRemoteService::class.java)

    fun getRecyclerListObserver(): MutableLiveData<ArrayList<Note>>
    {
        return  recyclerListLiveData
    }

    fun getCreateNoteObservable(): MutableLiveData<NoteResponse>
    {
        return createNoteLiveData
    }

    fun getUpdateNoteObservable(): MutableLiveData<UpdateNoteResponse>
    {
        return updateNoteLiveData
    }

    fun getDeleteNoteObservable(): MutableLiveData<DeleteNoteResponse>
    {
        return deleteNoteLiveData
    }

    fun getUserNotes(userID: Int)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val response = retroInstance.getUserNotes(userID)
            recyclerListLiveData.postValue(response)
        }
    }

    fun addNote(note: CreateNote)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val response = retroInstance.addNote(note)

            try
            {
                if (!response.isSuccessful)
                {
                    createNoteLiveData.postValue(null)
                    return@launch
                }
            }
            catch (error: HttpException)
            {
                print(error)
                createNoteLiveData.postValue(null)
            }

            createNoteLiveData.postValue(response.body())
        }
    }

    fun updateNote(updatedData: UpdateNote, context: Context)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val response = retroInstance.updateNote(updatedData)

            try
            {
                if(!response.isSuccessful)
                {
                    updateNoteLiveData.postValue(null)
                    return@launch
                }
            }
            catch (error: HttpException)
            {
                print(error)
                updateNoteLiveData.postValue(null)
            }

            updateNoteLiveData.postValue(response.body())
        }
    }

    fun deleteNote(noteID: Int, context: Context)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val response = retroInstance.deleteNote(noteID)

            try
            {
                if(!response.isSuccessful)
                {
                    deleteNoteLiveData.postValue(null)
                    return@launch
                }
            }
            catch (error: HttpException)
            {
                print(error)
                deleteNoteLiveData.postValue(null)
            }

            deleteNoteLiveData.postValue(response.body())
        }
    }
}