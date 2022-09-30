package com.example.notesmvvm.di

import android.content.Context
import com.example.notesmvvm.data.local.AppDatabase
import com.example.notesmvvm.data.remote.net.NoteRemoteService
import com.example.notesmvvm.data.remote.net.UserRemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "http://192.168.0.24:3000/"
    private val interceptor = HttpLoggingInterceptor()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit
    {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserRemoteService
    {
        return retrofit.create(UserRemoteService::class.java)
    }

    @Singleton
    @Provides
    fun provideNoteService(retrofit: Retrofit): NoteRemoteService
    {
        return retrofit.create(NoteRemoteService::class.java)
    }

    @Singleton
    @Provides
    fun provideNotesDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideUsersDao(db: AppDatabase) = db.usersDao()

    @Singleton
    @Provides
    fun provideNotesDao(db: AppDatabase) = db.notesDao()
}