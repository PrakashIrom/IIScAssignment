package com.apui.iiscassignment.ui.auth.authscreen

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apui.iiscassignment.R
import com.apui.iiscassignment.domain.repository.GmailAuthRepo
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface GmailAuthState {
    data class Authenticated(val user: FirebaseUser) : GmailAuthState
    data class Unauthenticated(val message: String? = null) : GmailAuthState
    data object Loading : GmailAuthState
    data object Idle : GmailAuthState
}

class GmailAuthViewModel(private val gmailAuthRepo: GmailAuthRepo, context: Context) : ViewModel() {

    private val _authState = MutableStateFlow<GmailAuthState>(GmailAuthState.Idle)
    val authState = _authState.asStateFlow()

    private val appContext = context.applicationContext

    private val credentialManager by lazy {
        CredentialManager.create(appContext)
    }

    private val signInRequest by lazy {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(appContext.getString(R.string.default_web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .build()

        GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    fun signIn(email: String, password: String) {
        _authState.value = GmailAuthState.Loading
        try {
            gmailAuthRepo.signInUserAccount(email, password) { result ->
                result.onSuccess { user ->
                    _authState.value = GmailAuthState.Authenticated(user)
                }.onFailure { e ->
                    Log.e("GmailAuthViewModel", "Sign-in failed", e)
                    _authState.value = GmailAuthState.Unauthenticated(e.message)
                }
            }
        } catch (e: Exception) {
            Log.e("SignIn", "Sign-in failed", e)
            _authState.value = GmailAuthState.Unauthenticated(e.message)
        }
    }

    fun signUp(email: String, password: String) {
        _authState.value = GmailAuthState.Loading
        try {
            gmailAuthRepo.signUpUserAccount(email, password) { result ->
                result.onSuccess { user ->
                    _authState.value = GmailAuthState.Authenticated(user)
                }.onFailure { e ->
                    Log.e("GmailAuthViewModel", "Sign-up failed", e)
                    _authState.value = GmailAuthState.Unauthenticated(e.message)
                }
            }
        } catch (e: Exception) {
            Log.e("SignUp", "Sign-up failed", e)
            _authState.value = GmailAuthState.Unauthenticated(e.message)
        }
    }

    fun requestSignIn(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value = GmailAuthState.Loading
            try {
                val result = credentialManager.getCredential(
                    context = context,
                    request = signInRequest
                )
                gmailAuthRepo.handleSignIn(result.credential) { result ->
                    result.onSuccess { user ->
                        _authState.value = GmailAuthState.Authenticated(user)
                    }.onFailure { e ->
                        Log.e("GmailAuthViewModel", "Sign-in failed", e)
                        _authState.value = GmailAuthState.Unauthenticated(e.message)
                    }
                }
            } catch (e: NoCredentialException) {
                Log.w("SignInViewModel", "No saved credentials", e)
                _authState.value = GmailAuthState.Unauthenticated(e.message)
            } catch (e: Exception) {
                Log.e("SignInViewModel", "Sign-in failed: ${e.message}", e)
                _authState.value = GmailAuthState.Unauthenticated(e.message)
            }
        }
    }
}
