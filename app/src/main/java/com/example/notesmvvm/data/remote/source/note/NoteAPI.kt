package com.example.notesmvvm.data.remote.source.note

import com.example.notesmvvm.SimpleResponse
import com.example.notesmvvm.data.remote.model.note.*
import com.example.notesmvvm.data.remote.net.NoteRemoteService
import retrofit2.Response

class NoteAPI (private val noteService: NoteRemoteService)
{
    suspend fun getUserNotes(userID: Int): ArrayList<Note>
    {
        return noteService.getUserNotes(userID)
    }

    suspend fun createNote(note: CreateNoteRequest): SimpleResponse<CreateNoteResponse>
    {
        return SimpleResponse.safeAPICall {
            noteService.createNote(note)
        }
    }

    suspend fun updateNote(updatedData: UpdateNote): SimpleResponse<UpdateNoteResponse>
    {
        return SimpleResponse.safeAPICall {
            noteService.updateNote(updatedData)
        }
    }

    suspend fun deleteNote(noteID: Int): SimpleResponse<DeleteNoteResponse>
    {
        return SimpleResponse.safeAPICall {
            noteService.deleteNote(noteID)
        }
    }
}