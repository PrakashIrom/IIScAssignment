package com.apui.iiscassignment.ui.auth.authscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.apui.iiscassignment.domain.repository.GmailAuthRepo
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface GmailAuthState {
    data class Authenticated(val user: FirebaseUser) : GmailAuthState
    data class Unauthenticated(val message: String? = null) : GmailAuthState
    data object Loading : GmailAuthState
    data object Idle : GmailAuthState
}

class GmailAuthViewModel(private val gmailAuthRepo: GmailAuthRepo) : ViewModel() {
    private val _authState = MutableStateFlow<GmailAuthState>(GmailAuthState.Idle)
    val authState = _authState.asStateFlow()

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
}
