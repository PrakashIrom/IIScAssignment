package com.apui.iiscassignment.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.apui.iiscassignment.ui.HomeScreen
import com.apui.iiscassignment.ui.auth.signin.AuthState
import com.apui.iiscassignment.ui.auth.signin.SignInScreen
import com.apui.iiscassignment.ui.auth.signin.SignInViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavHost(navController: NavHostController) {
    /*val context = LocalContext.current
    val viewModel: SignInViewModel = koinViewModel(parameters = { parametersOf(context) })
    val authState = viewModel.authState.collectAsState().value*/

    NavHost(
        navController = navController,
        startDestination = "signin"
    ) {
        composable("signin") {
            SignInScreen(navController = navController)
        }
        composable("home") {
            HomeScreen()
        }
    }

}