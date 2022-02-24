package com.example.notesmvvm.data.remote.net

import com.example.notesmvvm.data.remote.model.note.*
import retrofit2.Response
import retrofit2.http.*

interface NoteRemoteService {
    @GET("note/{id}")
    suspend fun getUserNotes(
        @Path("id") userID: Int
    ): ArrayList<Note>

    @POST("note")
    suspend fun addNote(@Body note: CreateNote): Response<NoteResponse>

    @PUT("note")
    suspend fun updateNote(@Body note: UpdateNote): Response<UpdateNoteResponse>

    @DELETE("note/{id}")
    suspend fun deleteNote(@Path("id") id: Int): Response<DeleteNoteResponse>
}