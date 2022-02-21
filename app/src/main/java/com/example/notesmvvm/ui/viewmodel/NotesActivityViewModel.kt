package com.example.notesmvvm.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesmvvm.data.model.note.CreateNote
import com.example.notesmvvm.data.model.note.Note
import com.example.notesmvvm.data.model.note.UpdateNote
import com.example.notesmvvm.data.remote.net.NoteRemoteService
import com.example.notesmvvm.data.remote.source.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class NotesActivityViewModel: ViewModel() {
    private var recyclerListLiveData: MutableLiveData<ArrayList<Note>> = MutableLiveData()
    private var createNoteLiveData: MutableLiveData<CreateNote> = MutableLiveData()
    private val retroInstance: NoteRemoteService = RetrofitBuilder.getRetrofit().create(NoteRemoteService::class.java)

    fun getRecyclerListObserver(): MutableLiveData<ArrayList<Note>>
    {
        return  recyclerListLiveData
    }

    fun getCreateNoteObservable(): MutableLiveData<CreateNote>
    {
        return createNoteLiveData
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

            if (response.isSuccessful)
            {
                createNoteLiveData.postValue(response.body())
                //getUserNotes(note.userID)
            }
            else
                createNoteLiveData.postValue(null)
        }
    }

    fun updateNote(updatedData: UpdateNote, context: Context)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val response = retroInstance.updateNote(updatedData)

            withContext(Dispatchers.Main)
            {
                try
                {
                    if (response.isSuccessful)
                    {
                        getUserNotes(updatedData.userID)
                        Toast.makeText(context, "Note updated successfully", Toast.LENGTH_LONG).show()
                    }
                }
                catch (error: HttpException)
                {
                    print(error)
                    Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun deleteNote(noteID: Int, context: Context)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val response = retroInstance.deleteNote(noteID)

            withContext(Dispatchers.Main)
            {
                try
                {
                    if (response.isSuccessful)
                    {
                        Toast.makeText(context, "Note deleted successfully", Toast.LENGTH_LONG).show()
                    }
                }
                catch (error: HttpException)
                {
                    print(error)
                    Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}