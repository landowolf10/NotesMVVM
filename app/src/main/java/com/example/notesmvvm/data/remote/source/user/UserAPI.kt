package com.example.notesmvvm.data.remote.source.user

import com.example.notesmvvm.SimpleResponse
import com.example.notesmvvm.data.remote.model.user.login.LoginRequest
import com.example.notesmvvm.data.remote.model.user.login.LoginResponse
import com.example.notesmvvm.data.remote.net.UserRemoteService
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class UserAPI @Inject constructor(private val userService: UserRemoteService)
{
    suspend fun login(loginRequest: LoginRequest): SimpleResponse<LoginResponse>
    {
        return SimpleResponse.safeAPICall {
            userService.login(loginRequest)
        }
    }
}