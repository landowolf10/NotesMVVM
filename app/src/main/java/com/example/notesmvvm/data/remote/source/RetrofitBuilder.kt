package com.example.notesmvvm.data.remote.source

import com.example.notesmvvm.data.remote.net.NoteRemoteService
import com.example.notesmvvm.data.remote.net.UserRemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitBuilder {
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
    fun provideUserApiClient(retrofit: Retrofit): UserRemoteService
    {
        return retrofit.create(UserRemoteService::class.java)
    }

    @Singleton
    @Provides
    fun provideNoteApiClient(retrofit: Retrofit): NoteRemoteService
    {
        return retrofit.create(NoteRemoteService::class.java)
    }

    /*private val noteService: NoteRemoteService by lazy {
        getRetrofit().create(NoteRemoteService::class.java)
    }

    private val userService: UserRemoteService by lazy {
        getRetrofit().create(UserRemoteService::class.java)
    }

    val noteAPI = NoteAPI(noteService)
    val userAPI = UserAPI(userService)*/
}