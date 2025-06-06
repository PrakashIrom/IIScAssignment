package com.apui.iiscassignment.data.repository

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import com.apui.iiscassignment.domain.repository.AuthRepository
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AuthRepositoryImpl(private val auth: FirebaseAuth): AuthRepository {
    override fun handleSignIn(credential: Credential, checkState: (FirebaseUser) -> Unit) {
        // Check if credential is of type Google ID
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            // Create Google ID Token
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            // Sign in to Firebase with using the token
            firebaseAuthWithGoogle(googleIdTokenCredential.idToken, checkState)
        } else {
            Log.w("AuthRepository", "Credential is not of type Google ID!")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String, checkState: (FirebaseUser) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("SignIn Success", "signInWithCredential:success")
                val user = auth.currentUser!!
                checkState(user)
            } else {
                // If sign in fails, display a message to the user
                Log.w("SignIn Fail", "signInWithCredential:failure", task.exception)
                //updateUI(null)
            }
        }
    }
}