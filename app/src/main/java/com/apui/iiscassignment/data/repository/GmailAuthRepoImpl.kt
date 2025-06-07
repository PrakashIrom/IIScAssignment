package com.apui.iiscassignment.data.repository

import com.apui.iiscassignment.domain.repository.GmailAuthRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class GmailAuthRepoImpl(private val auth: FirebaseAuth) : GmailAuthRepo {
    override fun signInUserAccount(email: String, password: String, callback: (Result<FirebaseUser>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Result.success(auth.currentUser!!))
                } else {
                    callback(Result.failure(task.exception ?: Exception("Unknown error")))
                }
            }
    }

    override fun signUpUserAccount(email: String, password: String, callback: (Result<FirebaseUser>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Result.success(auth.currentUser!!))
                } else {
                    callback(Result.failure(task.exception ?: Exception("Unknown error")))
                }
            }
    }
}
