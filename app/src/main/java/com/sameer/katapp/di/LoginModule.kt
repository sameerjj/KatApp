package com.sameer.katapp.di

import com.sameer.katapp.repository.LoginDataSource
import com.sameer.katapp.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object LoginModule {

    @Provides
    fun provideLoginRepository(dataSource: LoginDataSource): LoginRepository = LoginRepository(dataSource)

    @Provides
    fun provideLoginDataSource(): LoginDataSource = LoginDataSource()
}