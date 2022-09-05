package com.example.notesmvvm.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notesmvvm.data.model.note.CreateNoteRequest
import com.example.notesmvvm.data.model.note.NoteResponse

@Dao
interface NotesDao
{
    @Query("SELECT * FROM notes")
    fun getAllNotes() : LiveData<List<NoteResponse>>

    @Query("SELECT * FROM notes WHERE userID = :userID")
    fun getUserNotes(userID: Int): LiveData<NoteResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CreateNoteRequest>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CreateNoteRequest)
}