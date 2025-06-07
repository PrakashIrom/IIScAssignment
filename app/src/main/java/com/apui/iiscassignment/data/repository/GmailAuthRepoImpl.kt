package com.apui.iiscassignment.data.repository

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import com.apui.iiscassignment.domain.repository.GmailAuthRepo
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class GmailAuthRepoImpl(private val auth: FirebaseAuth) : GmailAuthRepo {
    override fun signInUserAccount(
        email: String,
        password: String,
        callback: (Result<FirebaseUser>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Result.success(auth.currentUser!!))
                } else {
                    callback(Result.failure(task.exception ?: Exception("Unknown error")))
                }
            }
    }

    override fun signUpUserAccount(
        email: String,
        password: String,
        callback: (Result<FirebaseUser>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Result.success(auth.currentUser!!))
                } else {
                    callback(Result.failure(task.exception ?: Exception("Unknown error")))
                }
            }
    }

    override fun handleSignIn(
        credential: Credential,
        callback: (Result<FirebaseUser>) -> Unit
    ) {
        try {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                firebaseAuthWithGoogle(googleIdTokenCredential.idToken, callback)
            } else {
                callback(Result.failure(Exception("Unexpected credential type")))
            }
        } catch (e: Exception) {
            callback(Result.failure(e))
        }
    }

    private fun firebaseAuthWithGoogle(
        idToken: String,
        callback: (Result<FirebaseUser>) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("SignIn Success", "signInWithCredential:success")
                callback(Result.success(auth.currentUser!!))
            } else {
                // If sign in fails, display a message to the user
                Log.w("SignIn Fail", "signInWithCredential:failure", task.exception)
                callback(Result.failure(task.exception ?: Exception("Unknown error")))
                //updateUI(null)
            }
        }
    }

}
