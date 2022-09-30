package com.example.notesmvvm.data.remote.source.note

import com.example.notesmvvm.data.model.note.*
import com.example.notesmvvm.data.remote.net.NoteRemoteService
import com.example.notesmvvm.data.remote.source.BaseDataSource
import javax.inject.Inject

class NoteRemoteDataSource @Inject constructor(
    private val noteRemoteService: NoteRemoteService
)
{
    suspend fun getUserNotes(userID: Int): ArrayList<NoteResponse>
    {
        return noteRemoteService.getUserNotes(userID)
    }

    suspend fun getAllNotes(): ArrayList<NoteResponse>
    {
        return noteRemoteService.getAllNotes()
    }

    suspend fun createNote(note: CreateNoteRequest) = BaseDataSource.getResult {
        noteRemoteService.createNote(note)
    }

    suspend fun updateNote(updatedData: UpdateNote) = BaseDataSource.getResult {
        noteRemoteService.updateNote(updatedData)
    }

    suspend fun deleteNote(noteID: Int) = BaseDataSource.getResult {
        noteRemoteService.deleteNote(noteID)
    }
}