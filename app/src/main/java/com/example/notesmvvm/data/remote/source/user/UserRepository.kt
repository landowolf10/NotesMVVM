package com.example.notesmvvm.data.remote.source.user

import androidx.lifecycle.MutableLiveData
import com.example.notesmvvm.data.remote.model.user.login.LoginRequest
import com.example.notesmvvm.data.remote.model.user.login.LoginResponse
import com.example.notesmvvm.data.remote.net.UserRemoteService
import com.example.notesmvvm.data.remote.source.RetrofitBuilder
import retrofit2.HttpException

class UserRepository
{
    private val retroInstance: UserRemoteService = RetrofitBuilder.getRetrofit().create(
        UserRemoteService::class.java)
    private var loginLiveData: MutableLiveData<LoginResponse> = MutableLiveData()

    fun getLoginLiveData(): MutableLiveData<LoginResponse>
    {
        return loginLiveData
    }

    suspend fun login(user: LoginRequest)
    {
        val response = retroInstance.login(user)

        try
        {
            if (!response.isSuccessful)
            {
                loginLiveData.postValue(null)
            }
        }
        catch (error: HttpException)
        {
            print(error)
            loginLiveData.postValue(null)
        }

        loginLiveData.postValue(response.body())
    }
}