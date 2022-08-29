package com.example.notesmvvm.data.remote.source.user

import androidx.lifecycle.MutableLiveData
import com.example.notesmvvm.data.remote.model.user.login.LoginRequest
import com.example.notesmvvm.data.remote.model.user.login.LoginResponse
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI)
{
    private var loginLiveData: MutableLiveData<LoginResponse> = MutableLiveData()

    fun getLoginLiveData(): MutableLiveData<LoginResponse>
    {
        return loginLiveData
    }

    suspend fun login(user: LoginRequest): LoginResponse?
    {
        val request = userAPI.login(user)

        if(request.failed)
            return null

        if (!request.isSuccessful)
        {
            return null
        }

        return request.body
    }
}