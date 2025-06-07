package com.apui.iiscassignment.domain.repository

import androidx.credentials.Credential
import com.google.firebase.auth.FirebaseUser

interface GmailAuthRepo {
    fun signInUserAccount(email: String, password: String, callback: (Result<FirebaseUser>) -> Unit)
    fun signUpUserAccount(email: String, password: String, callback: (Result<FirebaseUser>) -> Unit)
    fun handleSignIn(credential: Credential, callback: (Result<FirebaseUser>) -> Unit)
}
