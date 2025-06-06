package com.apui.iiscassignment.domain.repository

import androidx.credentials.Credential

interface AuthRepository {
    fun handleSignIn(credential: Credential)
}