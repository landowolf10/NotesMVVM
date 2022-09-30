package com.example.notesmvvm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesmvvm.data.model.note.CreateNoteRequest
import com.example.notesmvvm.data.model.note.NoteResponse
import com.example.notesmvvm.data.model.user.login.LoginData

@Database(entities = [NoteResponse::class, LoginData::class, CreateNoteRequest::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase()
{
    abstract fun notesDao(): NotesDao
    abstract fun usersDao(): UsersDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "notes_db")
                .fallbackToDestructiveMigration()
                .build()
    }
}