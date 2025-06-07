package com.apui.iiscassignment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import org.koin.compose.koinInject

@Composable
fun HomeScreen() {
    val auth = koinInject<FirebaseAuth>()
    val currentUser = auth.currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        currentUser?.let { user ->

            user.photoUrl?.let { photoUri ->
                AsyncImage(
                    model = photoUri,
                    contentDescription = "Profile Photo",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(text = "Name: ${user.displayName ?: "N/A"}")
            Text(text = "Email: ${user.email ?: "N/A"}")
            Text(text = "UID: ${user.uid}")
            Text(text = "Email Verified: ${user.isEmailVerified}")
        } ?: run {
            Text("No user is signed in.")
        }
    }
}