package com.sameer.katapp.repository

import com.sameer.katapp.data.Resource
import com.sameer.katapp.model.CatImage

/// currently this repo only exists in case caching was to be added in the future
class CatRepository(private val catApiDataSource: CatApiDataSource) {

    suspend fun getRandomCatImage(): Resource<CatImage> {
        val resource = catApiDataSource.getRandomCatImage()
        return when(resource){
            is Resource.Success -> {
                val data = resource.data.firstOrNull()
                if (data != null){
                    Resource.Success(data)
                } else {
                    Resource.Error("")
                }
            }
            is Resource.Loading -> Resource.Loading()
            is Resource.Error -> Resource.Error(resource.message, null, resource.exception)
        }
    }
}