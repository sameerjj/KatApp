package com.sameer.katapp.data

import java.lang.Exception

// A generic class that contains data and status about loading this data.
sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Loading<T> : Resource<T>()
    class Error<T>(val message: String, val data: T? = null, val exception: Exception? = null) : Resource<T>()
}