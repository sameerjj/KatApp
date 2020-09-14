package com.sameer.katapp.repository.LoginRepository

import com.sameer.katapp.repository.LoginDataSource
import com.sameer.katapp.repository.LoginRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.verify
import com.sameer.katapp.data.Result
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class LoginRepositoryTest {

    private lateinit var dataSource: LoginDataSource

    private lateinit var repo: LoginRepository

    @Before
    fun setup() {
        dataSource = LoginDataSource()
        repo = LoginRepository(dataSource)
    }

    @Test
    fun `correct credentials should have success result`() = runBlocking {
        val result = repo.login("username", "password")
        Assert.assertTrue(result is Result.Success)
    }

    @Test
    fun `incorrect credentials should have error result`() = runBlocking {
        val result = repo.login("bleh", "password")
        Assert.assertTrue(result is Result.Error)
    }
}