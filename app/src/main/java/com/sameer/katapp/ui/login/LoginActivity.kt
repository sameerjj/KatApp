package com.sameer.katapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sameer.katapp.R
import com.sameer.katapp.data.Resource
import com.sameer.katapp.model.LoggedInUser
import com.sameer.katapp.ui.image_viewer.ImageViewerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    val loginViewModel: LoginViewModel by viewModels()

    lateinit var usernameEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton: Button
    lateinit var loadingView: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.login)
        loadingView = findViewById(R.id.loading)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            loginButton.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                usernameEditText.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                passwordEditText.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult is Resource.Loading) {
                loadingView.visibility = View.VISIBLE
            } else {
                loadingView.visibility = View.GONE
            }

            when (loginResult){
                is Resource.Success -> {
                    loadingView.visibility = View.GONE
                    updateUiWithUser(loginResult.data)
                }
                is Resource.Loading -> {
                    loadingView.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    loadingView.visibility = View.GONE
                    showLoginFailed(loginResult.message)
                }
            }
        })

        usernameEditText.afterTextChanged {
            loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
            )
        }

        passwordEditText.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        usernameEditText.text.toString(),
                        passwordEditText.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                                usernameEditText.text.toString(),
                                passwordEditText.text.toString()
                        )
                }
                false
            }

            loginButton.setOnClickListener {
                loadingView.visibility = View.VISIBLE
                loginViewModel.login(usernameEditText.text.toString(), passwordEditText.text.toString())
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUser) {
        val welcome = getString(R.string.welcome)
        val displayName = model.username

        val intent = Intent(this, ImageViewerActivity::class.java)
        startActivity(intent)
    }

    private fun showLoginFailed(errorMessage: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.login_failed)
            .setMessage(errorMessage)
            .setPositiveButton(R.string.ok) { _, _ ->
                passwordEditText.text.clear()
            }
            .show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}