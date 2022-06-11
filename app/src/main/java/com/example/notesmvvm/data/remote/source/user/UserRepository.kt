package com.example.notesmvvm.data.remote.source.user

import androidx.lifecycle.MutableLiveData
import com.example.notesmvvm.data.remote.model.user.login.LoginRequest
import com.example.notesmvvm.data.remote.model.user.login.LoginResponse
import com.example.notesmvvm.data.remote.net.UserRemoteService
import com.example.notesmvvm.data.remote.source.RetrofitBuilder
import retrofit2.HttpException

class UserRepository
{
    private var loginLiveData: MutableLiveData<LoginResponse> = MutableLiveData()

    fun getLoginLiveData(): MutableLiveData<LoginResponse>
    {
        return loginLiveData
    }

    suspend fun login(user: LoginRequest): LoginResponse?
    {
        val request = RetrofitBuilder.userAPI.login(user)

        if(request.failed)
            return null

        if (!request.isSuccessful)
        {
            return null
        }

        return request.body
    }
}