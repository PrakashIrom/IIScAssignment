package com.apui.iiscassignment.ui.auth.authscreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import org.koin.core.parameter.parametersOf

@Composable
fun SignUpScreen(
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context: Context = LocalContext.current
    val viewModel: GmailAuthViewModel =
        org.koin.compose.viewmodel.koinViewModel(parameters = { parametersOf(context) })
    val authState = viewModel.authState.collectAsStateWithLifecycle().value

    LaunchedEffect(authState) {
        when (authState) {
            is GmailAuthState.Authenticated -> {
                Toast.makeText(context, "Sign-up successful!", Toast.LENGTH_SHORT).show()
                navController.navigate("signin") {
                    popUpTo("signup") { inclusive = true }
                }
            }

            is GmailAuthState.Unauthenticated -> {
                Toast.makeText(context, authState.message ?: "Sign-up failed", Toast.LENGTH_SHORT)
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
        Text("Sign Up", style = MaterialTheme.typography.headlineMedium)

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
                viewModel.signUp(email, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Already have an account? Sign In",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable { navController.navigate("signin") }
                .padding(8.dp)
        )
    }
}
