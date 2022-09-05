package com.example.notesmvvm.utils

data class Resource<T>(
    val status: Status,
    val data: T?,
    val message: String?
)
{
    sealed class Status {
        object SUCCESS: Status()
        object ERROR: Status()
        object LOADING: Status()
    }

    companion object
    {
        fun <T> success(data: T): Resource<T>
        {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T>
        {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T>
        {
            return Resource(Status.LOADING, data, null)
        }
    }
}
