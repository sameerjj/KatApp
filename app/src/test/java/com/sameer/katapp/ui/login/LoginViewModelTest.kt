package com.sameer.katapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sameer.katapp.LifeCycleTestOwner
import com.sameer.katapp.R
import com.sameer.katapp.data.Result
import com.sameer.katapp.model.LoggedInUser
import com.sameer.katapp.repository.LoginDataSource
import com.sameer.katapp.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @Mock
    private lateinit var dataSource: LoginDataSource

    private val repo: LoginRepository = mock()
    private val loginObserver: Observer<LoginResult> = mock()

    private lateinit var viewModel: LoginViewModel

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()
        viewModel = LoginViewModel(repo)
        viewModel.loginResult.observe(lifeCycleTestOwner, loginObserver)
    }

    @Test
    fun `successful login calls the repo login method and shows successful result`() = testDispatcher.runBlockingTest {
        lifeCycleTestOwner.onResume()
        When calling repo.login("username", "password") itReturns Result.Success(LoggedInUser("username"))

        viewModel.login("username", "password")

        Verify on repo that repo.login("username", "password") was called
        Verify on loginObserver that loginObserver.onChanged(LoginResult(success = LoggedInUserView(displayName = "username"))) was called
    }

    @Test
    fun `incorrect login calls the repo login method and shows error result`() = testDispatcher.runBlockingTest {
        lifeCycleTestOwner.onResume()
        val exception = Exception("test")
        When calling repo.login("bleh", "password") itReturns Result.Error(exception)

        viewModel.login("bleh", "password")

        Verify on repo that repo.login("bleh", "password") was called
        Verify on loginObserver that loginObserver.onChanged(LoginResult(error = R.string.login_failed)) was called
    }

    //todo more tests regarding login form validation (small password, invalid characters)

    @After
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}