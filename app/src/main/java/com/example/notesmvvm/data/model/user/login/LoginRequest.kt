package com.example.notesmvvm.data.model.user.login

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

data class LoginData(
    @SerializedName("id")
    var userID: Int,
    @SerializedName("nombre")
    var userName: String,
    @SerializedName("correo")
    var userMail: String,
    @SerializedName("pass")
    var userPassword: String
)