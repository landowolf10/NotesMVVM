package com.example.notesmvvm.data.remote.source.note

import com.example.notesmvvm.SimpleResponse
import com.example.notesmvvm.data.remote.model.note.*
import com.example.notesmvvm.data.remote.net.NoteRemoteService
import javax.inject.Inject

class NoteAPI @Inject constructor(private val noteRemoteService: NoteRemoteService)
{
    suspend fun getUserNotes(userID: Int): ArrayList<NoteResponse>
    {
        return noteRemoteService.getUserNotes(userID)
    }

    suspend fun createNote(note: CreateNoteRequest): SimpleResponse<CreateNoteResponse>
    {
        return SimpleResponse.safeAPICall {
            noteRemoteService.createNote(note)
        }
    }

    suspend fun updateNote(updatedData: UpdateNote): SimpleResponse<UpdateNoteResponse>
    {
        return SimpleResponse.safeAPICall {
            noteRemoteService.updateNote(updatedData)
        }
    }

    suspend fun deleteNote(noteID: Int): SimpleResponse<DeleteNoteResponse>
    {
        return SimpleResponse.safeAPICall {
            noteRemoteService.deleteNote(noteID)
        }
    }
}