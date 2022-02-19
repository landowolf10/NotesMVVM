package com.example.notesmvvm.data.remote.source.user

import android.content.Context
import android.content.Intent
import com.example.notesmvvm.data.model.user.login.LoginRequest
import com.example.notesmvvm.data.remote.net.UserRemoteService
import com.example.notesmvvm.data.remote.source.RetrofitBuilder
import com.example.notesmvvm.ui.views.NotesActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class UserRemoteDataSource
{
    private val service: UserRemoteService = RetrofitBuilder.getRetrofit().create(UserRemoteService::class.java)

    fun login(user: LoginRequest, context: Context)
    {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.login(user)

            withContext(Dispatchers.Main)
            {
                try
                {
                    if (response.isSuccessful)
                    {
                        val loginData = response.body()
                        val intent = Intent(context, NotesActivity::class.java)

                        if (loginData != null)
                            intent.putExtra("user_id", loginData.loginData.userID)

                        context.startActivity(intent)
                    }
                }
                catch (error: HttpException)
                {
                    print(error)
                }
            }
        }
    }
}