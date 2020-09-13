package com.sameer.katapp.network

import retrofit2.http.GET

interface CatApi {

    @GET("/v1/images/search")
    suspend fun getRandomCat()
}