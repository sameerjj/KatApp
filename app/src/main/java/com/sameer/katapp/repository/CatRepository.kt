package com.sameer.katapp.repository

import com.sameer.katapp.model.CatImage
import com.sameer.katapp.network.CatApi
import com.sameer.katapp.data.Result

class CatRepository(private val catApi: CatApi) {

    suspend fun getRandomCatImage(): Result<CatImage> {
        return try {
            //todo this value wrapping should likely happen in a DataSource
            val value = catApi.getRandomCatImage()
            Result.Success(value.first())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}