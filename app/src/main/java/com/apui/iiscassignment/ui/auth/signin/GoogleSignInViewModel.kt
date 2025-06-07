package com.apui.iiscassignment.ui.auth.signin

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apui.iiscassignment.domain.repository.GoogleAuthRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.apui.iiscassignment.R
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AuthState {
    data class Authenticated(val user: FirebaseUser) : AuthState
    data class Unauthenticated(val message: String? = null) : AuthState
    data object Loading : AuthState
    data object Idle : AuthState
}

class GoogleSignInViewModel(
    private val authRepository: GoogleAuthRepository,
    context: Context
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private val appContext = context.applicationContext

    private val credentialManager by lazy {
        CredentialManager.create(appContext)
    }

    private val signInRequest by lazy {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(appContext.getString(R.string.default_web_client_id))
            .setFilterByAuthorizedAccounts(true)
            .build()

        GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    fun requestSignIn() {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value = AuthState.Loading
            try {
                val result = credentialManager.getCredential(
                    context = appContext,
                    request = signInRequest
                )
                authRepository.handleSignIn(result.credential) { user ->
                    _authState.value = AuthState.Authenticated(user)
                }
            } catch (e: Exception) {
                Log.e("SignInViewModel", "Sign-in failed: ${e.message}", e)
                _authState.value = AuthState.Unauthenticated(e.message)
            }
        }
    }
}
