package com.example.notesmvvm.data.remote.source.note

import androidx.lifecycle.MutableLiveData
import com.example.notesmvvm.data.remote.model.note.*
import com.example.notesmvvm.data.remote.net.NoteRemoteService
import com.example.notesmvvm.data.remote.source.RetrofitBuilder
import retrofit2.HttpException

class NoteRepository {
    private var recyclerListLiveData: MutableLiveData<ArrayList<Note>> = MutableLiveData()
    private var createNoteLiveData: MutableLiveData<NoteResponse> = MutableLiveData()
    private var updateNoteLiveData: MutableLiveData<UpdateNoteResponse> = MutableLiveData()
    private var deleteNoteLiveData: MutableLiveData<DeleteNoteResponse> = MutableLiveData()

    fun getNoteLiveData(): MutableLiveData<ArrayList<Note>>
    {
        return  recyclerListLiveData
    }

    fun getCreateNoteLiveData(): MutableLiveData<NoteResponse>
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

    suspend fun getUserNotes(userID: Int): ArrayList<Note>
    {
        val response = RetrofitBuilder.noteAPI.getUserNotes(userID)

        try
        {
            if (response.isEmpty())
            {
                recyclerListLiveData.postValue(null)
            }
        }
        catch (error: HttpException)
        {
            print(error)
            recyclerListLiveData.postValue(null)
        }

        recyclerListLiveData.postValue(response)

        return response
    }

    suspend fun addNote(note: CreateNote)
    {
        val response = RetrofitBuilder.noteAPI.createNote(note)

        try
        {
            if (!response.isSuccessful)
            {
                createNoteLiveData.postValue(null)
            }
        }
        catch (error: HttpException)
        {
            print(error)
            createNoteLiveData.postValue(null)
        }

        createNoteLiveData.postValue(response.body())
        RetrofitBuilder.noteAPI.getUserNotes(note.userID)
    }

    suspend fun updateNote(updatedData: UpdateNote)
    {
        val response = RetrofitBuilder.noteAPI.updateNote(updatedData)

        try
        {
            if (!response.isSuccessful)
            {
                updateNoteLiveData.postValue(null)
            }
        }
        catch (error: HttpException)
        {
            print(error)
            updateNoteLiveData.postValue(null)
        }

        updateNoteLiveData.postValue(response.body())
    }

    suspend fun deleteNote(noteID: Int)
    {
        val response = RetrofitBuilder.noteAPI.deleteNote(noteID)

        try
        {
            if(!response.isSuccessful)
            {
                deleteNoteLiveData.postValue(null)
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