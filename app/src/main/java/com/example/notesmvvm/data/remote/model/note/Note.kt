package com.example.notesmvvm.data.remote.model.note

import com.google.gson.annotations.SerializedName

data class NoteResponse(
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

data class CreateNoteRequest(
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

data class CreateNoteResponse(
    @SerializedName("message")
    var message: String,
    @SerializedName("data")
    var data: CreateNoteRequest
)

data class UpdateNoteData(
    @SerializedName("id")
    var noteID: Int,
    @SerializedName("titulo")
    var title: String,
    @SerializedName("contenido")
    var content: String
)

data class UpdateNoteResponse(
    @SerializedName("message")
    var message: String,
    @SerializedName("data")
    var data: UpdateNoteData
)

data class DeleteNoteResponse(
    @SerializedName("message")
    var message: String
)