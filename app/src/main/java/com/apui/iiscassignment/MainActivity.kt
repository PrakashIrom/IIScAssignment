package com.apui.iiscassignment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.apui.iiscassignment.ui.navigation.AppNavHost
import com.apui.iiscassignment.ui.theme.IIScAssignmentTheme
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val auth: FirebaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            IIScAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AppNavHost(navController)
                    }
                }
            }
        }
    }

    fun signUp(){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
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
    /* override fun onStart() {
         super.onStart()
         // Check if user is signed in (non-null) and update UI accordingly.
         val currentUser = auth.currentUser
     }*/
}
