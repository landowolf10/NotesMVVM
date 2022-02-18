package com.example.notesmvvm.data.remote.net

import com.example.notesmvvm.data.model.note.Note
import retrofit2.Response

class ApiClient(private val noteService: NoteRemoteService)
{
    suspend fun getUserNotes(userID: Int): Response<MutableList<Note>>
    {
        return noteService.getUserNotes(userID)
    }
}