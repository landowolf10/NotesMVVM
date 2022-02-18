package com.example.notesmvvm.data.remote.source

import com.example.notesmvvm.data.remote.net.ApiClient
import com.example.notesmvvm.data.remote.net.NoteRemoteService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private const val BASE_URL = "http://192.168.0.15:3000/"

    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor())
        .readTimeout(100, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val noteService: NoteRemoteService by lazy {
        retrofit.create(NoteRemoteService::class.java)
    }

    val apiClient = ApiClient(noteService)

    private fun loggingInterceptor(): HttpLoggingInterceptor
    {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}