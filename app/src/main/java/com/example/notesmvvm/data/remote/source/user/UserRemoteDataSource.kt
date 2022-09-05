package com.example.notesmvvm.data.remote.source.user

import com.example.notesmvvm.data.model.user.login.LoginRequest
import com.example.notesmvvm.data.remote.net.UserRemoteService
import com.example.notesmvvm.data.remote.source.BaseDataSource
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserRemoteService
)
{
    suspend fun login(loginRequest: LoginRequest) = BaseDataSource.getResult {
        userService.login(loginRequest)
    }
}