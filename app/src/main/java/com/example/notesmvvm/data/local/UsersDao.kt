package com.example.notesmvvm.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notesmvvm.data.model.user.login.LoginData
import com.example.notesmvvm.data.model.user.login.LoginRequest
import com.example.notesmvvm.data.model.user.login.LoginResponse
import com.example.notesmvvm.utils.Resource

@Dao
interface UsersDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(usersData: LoginData)

    //Al parecer LoginResponse no va a aqu+i, checar qué está fallando
    //Parece ser que funciona con LoginData,falta llamar estemétodo para comprobar (debe ser llamado
    // cuando no haya internet)
    @Query("SELECT * FROM users AS u WHERE u.userMail = :userMail AND u.userPassword = :userPassword")
    fun localLogin(userMail: String, userPassword: String): LiveData<LoginData>
}