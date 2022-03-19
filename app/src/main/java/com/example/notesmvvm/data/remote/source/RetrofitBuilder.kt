package com.example.notesmvvm.data.remote.source

import com.example.notesmvvm.data.remote.net.NoteRemoteService
import com.example.notesmvvm.data.remote.net.UserRemoteService
import com.example.notesmvvm.data.remote.source.note.NoteAPI
import com.example.notesmvvm.data.remote.source.user.UserAPI
import com.example.notesmvvm.ui.viewmodel.UserActivityViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private const val BASE_URL = "http://192.168.0.23:3000/"
    private val interceptor = HttpLoggingInterceptor()

    fun getRetrofit(): Retrofit
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

    private val noteService: NoteRemoteService by lazy {
        getRetrofit().create(NoteRemoteService::class.java)
    }

    private val userService: UserRemoteService by lazy {
        getRetrofit().create(UserRemoteService::class.java)
    }

    val noteAPI = NoteAPI(noteService)
    val userAPI = UserAPI(userService)
}