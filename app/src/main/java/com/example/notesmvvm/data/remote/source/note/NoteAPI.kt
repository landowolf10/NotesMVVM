package com.example.notesmvvm.data.remote.source.note

import com.example.notesmvvm.data.remote.model.note.*
import com.example.notesmvvm.data.remote.net.NoteRemoteService
import retrofit2.Response

class NoteAPI (private val noteService: NoteRemoteService)
{
    suspend fun getUserNotes(userID: Int): ArrayList<Note>
    {
        return noteService.getUserNotes(userID)
    }

    suspend fun createNote(note: CreateNote): Response<NoteResponse>
    {
        return noteService.createNote(note)
    }

    suspend fun updateNote(updatedData: UpdateNote): Response<UpdateNoteResponse>
    {
        return noteService.updateNote(updatedData)
    }

    suspend fun deleteNote(noteID: Int): Response<DeleteNoteResponse>
    {
        return noteService.deleteNote(noteID)
    }
}