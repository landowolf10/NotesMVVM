package com.example.notesmvvm.data.remote.model.user.create_user

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("nombre")
    var userName: String,
    @SerializedName("correo")
    var userMail: String,
    @SerializedName("pass")
    var userPassword: String
)