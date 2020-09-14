package com.sameer.katapp.data

import com.sameer.katapp.Constants
import com.sameer.katapp.data.model.LoggedInUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        return withContext(Dispatchers.IO){
            try {
                Thread.sleep(1000)
                if (username == Constants.HARCODE_USERNAME && password == Constants.HARCODE_PASSWORD){
                    val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
                    return@withContext Result.Success(fakeUser)
                } else {
                    return@withContext Result.Error(IOException("Error logging in"))
                }
            } catch (e: Throwable) {
                return@withContext Result.Error(IOException("Error logging in", e))
            }
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}