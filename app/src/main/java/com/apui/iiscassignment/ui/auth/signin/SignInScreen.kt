package com.apui.iiscassignment.ui.auth.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SignInScreen(navController: NavController) {

//    val context = LocalContext.current
//    val viewModel: GoogleSignInViewModel = koinViewModel(parameters = { parametersOf(context) })
//    val authState = viewModel.authState.collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
           /* viewModel.requestSignIn()
            if (authState is AuthState.Authenticated) navController.navigate("home") else Toast.makeText(
                context,
                "Sign In Failed",
                Toast.LENGTH_SHORT
            ).show()*/
        }) {
            Text("Google Sign In")
        }
    }
}