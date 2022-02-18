package com.example.notesmvvm.data.remote.net

import com.example.notesmvvm.data.model.user.login.LoginRequest
import com.example.notesmvvm.data.model.user.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRemoteService {
    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}