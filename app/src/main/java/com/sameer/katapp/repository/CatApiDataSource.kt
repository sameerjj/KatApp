package com.sameer.katapp.repository

import com.sameer.katapp.data.Resource
import com.sameer.katapp.model.CatImage
import com.sameer.katapp.network.CatApi

class CatApiDataSource(private val catApi: CatApi) : BaseDataSource() {

    suspend fun getRandomCatImage(): Resource<List<CatImage>> {
        return getResult { catApi.getRandomCatImage() }
    }

}