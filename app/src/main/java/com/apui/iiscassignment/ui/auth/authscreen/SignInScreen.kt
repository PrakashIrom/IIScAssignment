package com.apui.iiscassignment.ui.auth.authscreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apui.iiscassignment.R
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SignInScreen(navController: NavController) {

    val context = LocalContext.current
    val viewModel: GmailAuthViewModel = koinViewModel(parameters = { parametersOf(context) })
    val authState = viewModel.authState.collectAsStateWithLifecycle().value
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(authState) {
        when (authState) {
            is GmailAuthState.Authenticated -> {
                Toast.makeText(context, "Sign-in successful!", Toast.LENGTH_SHORT).show()
                navController.navigate("home") {
                    popUpTo("signin") { inclusive = true }
                }
            }

            is GmailAuthState.Unauthenticated -> {
                Toast.makeText(context, authState.message ?: "Sign-in failed", Toast.LENGTH_SHORT)
                    .show()
            }

            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sign In", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.signIn(email, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Don't have an account? Sign Up",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable { navController.navigate("signup") }
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = " OR ",
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.requestSignIn(context) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google_icon),
                contentDescription = "Google Sign-In",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sign in with Google",
                color = Color.Black
            )
        }

    }
}