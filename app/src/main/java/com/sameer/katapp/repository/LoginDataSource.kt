package com.sameer.katapp.repository

import com.sameer.katapp.Constants
import com.sameer.katapp.data.Resource
import com.sameer.katapp.model.LoggedInUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    suspend fun login(username: String, password: String): Resource<LoggedInUser> {
        return withContext(Dispatchers.IO){
            try {
                delay(1000)
                if (username == Constants.HARCODE_USERNAME && password == Constants.HARCODE_PASSWORD){
                    val fakeUser = LoggedInUser(username)
                    return@withContext Resource.Success(fakeUser)
                } else {
                    return@withContext Resource.Error("Error logging in")
                }
            } catch (e: Exception) {
                return@withContext Resource.Error("Error logging in",null, e)
            }
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}