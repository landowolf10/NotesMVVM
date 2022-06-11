package com.example.notesmvvm

import retrofit2.Response

data class SimpleResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: Exception?
)
{
    companion object
    {
        fun <T> success(data: Response<T>): SimpleResponse<T>
        {
            return SimpleResponse(
                status = Status.Success,
                data = data,
                exception = null
            )
        }

        fun <T> failure(exception: Exception): SimpleResponse<T>
        {
            return SimpleResponse(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }

        inline fun <T> safeAPICall(apiCall: () -> Response<T>): SimpleResponse<T>
        {
            return try
            {
                success(apiCall.invoke())
            }
            catch (e: java.lang.Exception)
            {
                failure(e)
            }
        }
    }

    sealed class Status
    {
        object Success: Status()
        object Failure: Status()
    }

    val isSuccessful: Boolean
    get() = !failed && this.data?.isSuccessful == true

    val body: T
    get() = this.data!!.body()!!

    val failed: Boolean
    get() = this.status == Status.Failure
}
