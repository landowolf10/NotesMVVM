package com.example.notesmvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesmvvm.data.remote.model.user.login.LoginRequest
import com.example.notesmvvm.data.remote.model.user.login.LoginResponse
import com.example.notesmvvm.data.remote.source.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserActivityViewModel: ViewModel() {
    private var loginLiveData: MutableLiveData<LoginResponse> = MutableLiveData()
    private var userRepository: UserRepository = UserRepository()

    init {
        loginLiveData = userRepository.getLoginLiveData()
    }

    fun getLoginLiveData(): MutableLiveData<LoginResponse>
    {
        return loginLiveData
    }

    fun login(user: LoginRequest)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository.login(user)

            loginLiveData.postValue(response)
        }
    }
}