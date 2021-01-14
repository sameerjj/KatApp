package com.sameer.katapp.di

import com.sameer.katapp.Constants
import com.sameer.katapp.network.AuthInterceptor
import com.sameer.katapp.network.CatApi
import com.sameer.katapp.repository.CatApiDataSource
import com.sameer.katapp.repository.CatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(AuthInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideCatApi(retrofit: Retrofit): CatApi {
        return retrofit.create(CatApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCatRepository(catApiDataSource: CatApiDataSource) = CatRepository(catApiDataSource)

    @Singleton
    @Provides
    fun provideCatDataSource(catApi: CatApi) = CatApiDataSource(catApi)
}