package com.apui.iiscassignment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.apui.iiscassignment.ui.HomeScreen
import com.apui.iiscassignment.ui.auth.authscreen.SignInScreen
import com.apui.iiscassignment.ui.auth.authscreen.SignUpScreen

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
        composable("signup") {
            SignUpScreen(navController = navController)
        }
    }

}