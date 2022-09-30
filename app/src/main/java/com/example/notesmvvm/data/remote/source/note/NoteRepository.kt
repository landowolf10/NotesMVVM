package com.example.notesmvvm.data.remote.source.note

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.notesmvvm.data.local.NotesDao
import com.example.notesmvvm.data.model.note.*
import com.example.notesmvvm.utils.CheckInternetConnection
import retrofit2.HttpException
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteRemoteDataSource: NoteRemoteDataSource,
    private val localNoteDataSource: NotesDao
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

    suspend fun getUserNotes(userID: Int, context: Context): ArrayList<NoteResponse>?
    {
        var response: ArrayList<NoteResponse>? = null
        var allNotesResponse: ArrayList<NoteResponse>? = null

        if(CheckInternetConnection.checkForInternet(context))
        {
            allNotesResponse = noteRemoteDataSource.getAllNotes()
            response = noteRemoteDataSource.getUserNotes(userID)

            allNotesResponse.let {
                localNoteDataSource.insertAllNotes(it)
            }
        }

        try
        {
            if (response?.isEmpty() == true)
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

        println("All notes: $allNotesResponse")

        return response
    }

    /*suspend fun getUserNotesLocal(userID: Int): ArrayList<NoteResponse>
    {
        val response = noteRemoteDataSource.getUserNotes(userID)

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
    }*/

    suspend fun addNote(note: CreateNoteRequest): CreateNoteResponse?
    {
        val response = noteRemoteDataSource.createNote(note)
        return response.data
    }

    suspend fun updateNote(updatedData: UpdateNote): UpdateNoteResponse?
    {
        val response = noteRemoteDataSource.updateNote(updatedData)
        return response.data
    }

    suspend fun deleteNote(noteID: Int): DeleteNoteResponse?
    {
        val response = noteRemoteDataSource.deleteNote(noteID)
        return response.data
    }
}