package com.apui.iiscassignment.domain.repository

import androidx.credentials.Credential
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun handleSignIn(credential: Credential, checkState: (FirebaseUser) -> Unit)
}