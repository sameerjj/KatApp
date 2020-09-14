package com.sameer.katapp.network
import com.sameer.katapp.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // only storing api key for example purposes
        val authenticatedRequest = request.newBuilder().addHeader("x-api-key", Constants.API_KEY).build()
        return chain.proceed(authenticatedRequest)
    }
}