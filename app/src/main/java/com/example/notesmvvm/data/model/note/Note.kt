package com.example.notesmvvm.data.model.note

import com.google.gson.annotations.SerializedName

data class Note(
    @SerializedName("id")
    var id: Int,
    @SerializedName("id_usuario")
    var userID: Int,
    @SerializedName("nombre_creador")
    var ownerName: String,
    @SerializedName("fecha")
    var date: String,
    @SerializedName("hora")
    var hour: String,
    @SerializedName("titulo")
    var title: String,
    @SerializedName("contenido")
    var content: String
)

data class CreateNote(
    @SerializedName("id_usuario")
    var userID: Int,
    @SerializedName("titulo")
    var title: String,
    @SerializedName("contenido")
    var content: String
)

data class UpdateNote(
    @SerializedName("id_usuario")
    var userID: Int,
    @SerializedName("id")
    var noteID: Int,
    @SerializedName("titulo")
    var title: String,
    @SerializedName("contenido")
    var content: String
)

data class NoteDeleted(
    @SerializedName("message")
    var message: String
)
