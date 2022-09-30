package com.example.notesmvvm.data.remote.source.user

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.notesmvvm.data.local.NotesDao
import com.example.notesmvvm.data.local.UsersDao
import com.example.notesmvvm.data.model.user.login.LoginData
import com.example.notesmvvm.data.model.user.login.LoginRequest
import com.example.notesmvvm.data.model.user.login.LoginResponse
import com.example.notesmvvm.utils.CheckInternetConnection
import com.example.notesmvvm.utils.Resource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userAPI: UserRemoteDataSource,
    private val localUserDataSource: UsersDao
)
{
    private var loginLiveData: MutableLiveData<LoginResponse> = MutableLiveData()
    private var localLoginLiveData: MutableLiveData<LoginData> = MutableLiveData()

    fun getLoginLiveData(): MutableLiveData<LoginResponse>
    {
        return loginLiveData
    }

    fun getLocalLoginLiveData(): MutableLiveData<LoginData>
    {
        return localLoginLiveData
    }

    suspend fun login(user: LoginRequest, context: Context): LoginResponse?
    {
        var request: Resource<LoginResponse>? = null
        //var userEmail: String
        //var userPassword: String

        //Check internet connection to call this
        if(CheckInternetConnection.checkForInternet(context))
        {
            request = userAPI.login(user)
            request.data?.let {
                localUserDataSource.insertAllUsers(it.loginData)
                //println("Local notes: " + localNotesDataSource.getAllNotes().toString())
            }
        }
        /*else
        {
            /*userEmail =

            userEmail = localUserDataSource.localLogin(user).value.email
            userPassword = localUserDataSource.localLogin(user).value.password

            //Crear un objeto con estos dos del modelo del usuario y asignarlo a request
            val user = LoginResponse(
                binding.etMail.text.toString(),
                binding.etPassword.text.toString()
            )*/

            request = localUserDataSource.localLogin(user)
        }*/

        println("Request data login: " + request?.data)


        return request?.data
    }

    suspend fun localLogin(user: LoginRequest) = localUserDataSource.localLogin(user.userMail, user.userPassword)
}