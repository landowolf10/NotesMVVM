package com.example.notesmvvm.data.model.user.login

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("correo")
    var userMail: String,
    @SerializedName("pass")
    var userPassword: String
)

data class LoginResponse(
    @SerializedName("message")
    var loginMessage: String,
    @SerializedName("user_data")
    var loginData: LoginData
)

@Entity(tableName = "users")
data class LoginData(
    @SerializedName("id")
    @PrimaryKey
    var userID: Int,
    @SerializedName("nombre")
    var userName: String,
    @SerializedName("correo")
    var userMail: String,
    @SerializedName("pass")
    var userPassword: String
)