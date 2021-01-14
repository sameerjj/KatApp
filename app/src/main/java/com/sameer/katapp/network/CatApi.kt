package com.sameer.katapp.network

import com.sameer.katapp.model.CatImage
import retrofit2.Response
import retrofit2.http.GET

interface CatApi {
    @GET("images/search")
    suspend fun getRandomCatImage(): Response<List<CatImage>>
}