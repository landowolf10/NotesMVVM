package com.example.notesmvvm.data.remote.source.note

import androidx.lifecycle.MutableLiveData
import com.example.notesmvvm.data.remote.model.note.*
import retrofit2.HttpException
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteAPI: NoteAPI
) {
    private var recyclerListLiveData: MutableLiveData<ArrayList<NoteResponse>> = MutableLiveData()
    private var createNoteLiveData: MutableLiveData<CreateNoteResponse> = MutableLiveData()
    private var updateNoteLiveData: MutableLiveData<UpdateNoteResponse> = MutableLiveData()
    private var deleteNoteLiveData: MutableLiveData<DeleteNoteResponse> = MutableLiveData()

    fun getNoteLiveData(): MutableLiveData<ArrayList<NoteResponse>>
    {
        return recyclerListLiveData
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

    suspend fun getUserNotes(userID: Int): ArrayList<NoteResponse>
    {
        val response = noteAPI.getUserNotes(userID)

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

    suspend fun getUserNotesLocal(userID: Int): ArrayList<NoteResponse>
    {
        val response = noteAPI.getUserNotes(userID)

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

    suspend fun addNote(note: CreateNoteRequest): CreateNoteResponse?
    {
        val response = noteAPI.createNote(note)

        if (response.failed)
            return null

        if (!response.isSuccessful)
            return null

        return response.body
    }

    suspend fun updateNote(updatedData: UpdateNote): UpdateNoteResponse?
    {
        val response = noteAPI.updateNote(updatedData)

        if (!response.isSuccessful || response.failed)
            return null

        return response.body
    }

    suspend fun deleteNote(noteID: Int): DeleteNoteResponse?
    {
        val response = noteAPI.deleteNote(noteID)

        if(!response.isSuccessful || response.failed)
            return null

        return response.body
    }
}