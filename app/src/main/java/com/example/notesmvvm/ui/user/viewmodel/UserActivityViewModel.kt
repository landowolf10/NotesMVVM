package com.example.notesmvvm.ui.user.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesmvvm.data.model.user.login.LoginData
import com.example.notesmvvm.data.model.user.login.LoginRequest
import com.example.notesmvvm.data.model.user.login.LoginResponse
import com.example.notesmvvm.data.remote.source.user.UserRepository
import com.example.notesmvvm.utils.CheckInternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserActivityViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private var loginLiveData: MutableLiveData<LoginResponse> = MutableLiveData()
    private var localLoginLiveData: LiveData<LoginData> = MutableLiveData()

    init {
        loginLiveData = userRepository.getLoginLiveData()
        localLoginLiveData = userRepository.getLocalLoginLiveData()
    }

    fun getLoginLiveData(): MutableLiveData<LoginResponse>
    {
        return loginLiveData
    }

    fun getLocalLoginLiveData(): LiveData<LoginData>
    {
        return localLoginLiveData
    }

    fun login(user: LoginRequest, context: Context)
    {
        viewModelScope.launch(Dispatchers.IO) {
            var response: LoginResponse? = null

            if(CheckInternetConnection.checkForInternet(context))
            {
                response = userRepository.login(user, context)
                println("Response user data online: $response")
            }

            loginLiveData.postValue(response)
        }
    }

    fun localLogin(user: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            val response: LiveData<LoginData> = userRepository.localLogin(user)
            println("Response user data online: $response")
            println("Local login live data: " + localLoginLiveData.value?.userID)

            localLoginLiveData.value
        }
    }
}