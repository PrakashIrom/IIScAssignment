package com.apui.iiscassignment.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.apui.iiscassignment.domain.repository.GmailAuthRepo
import com.google.firebase.auth.FirebaseAuth

class GmailAuthRepoImpl(private val auth: FirebaseAuth): GmailAuthRepo {

    override fun signInUserAccount(email:String, password:String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                    task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Success", "signInWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignIn fail", "signInWithEmail:failure", task.exception)
                    /*Toast.makeText(
                        ,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()*/
                    //updateUI(null)
                }
            }
    }

    override fun signUpUserAccount(email:String, password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignUp fail", "createUserWithEmail:failure", task.exception)
                    /*Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)*/
                }
            }
    }
}