package com.example.notesmvvm.data.remote.source

import com.example.notesmvvm.utils.Resource
import retrofit2.Response

abstract class BaseDataSource
{
    companion object {
        suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T>
        {
            try {
                val response = call()

                if (response.isSuccessful)
                {
                    val body = response.body()
                    if (body != null) return Resource.success(body)
                }

                return error("${response.code()} ${response.message()}")
            }
            catch (e: Exception)
            {
                return error(e.message ?: e.toString())
            }
        }

        private fun <T> error(message: String): Resource<T> {
            //Toast.makeText(this, "Failed to create note", Toast.LENGTH_LONG).show()
            return Resource.error("Network call has failed for a following reason: $message")
        }
    }
}