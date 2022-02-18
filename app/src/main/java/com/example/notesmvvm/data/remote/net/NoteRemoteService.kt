package com.example.notesmvvm.data.remote.net

import com.example.notesmvvm.data.model.note.CreateNote
import com.example.notesmvvm.data.model.note.Note
import com.example.notesmvvm.data.model.note.NoteDeleted
import com.example.notesmvvm.data.model.note.UpdateNote
import retrofit2.Response
import retrofit2.http.*

interface NoteRemoteService {
    @GET("note/{id}")
    suspend fun getUserNotes(
        @Path("id") userID: Int
    ): Response<MutableList<Note>>

    @POST("note")
    suspend fun addNote(@Body note: CreateNote): Response<CreateNote>

    @PUT("note")
    suspend fun updateNote(@Body note: UpdateNote): Response<UpdateNote>

    @DELETE("note/{id}")
    suspend fun deleteNote(@Path("id") id: Int): Response<NoteDeleted>
}