package com.apui.iiscassignment.di

import android.content.Context
import com.apui.iiscassignment.data.repository.AuthRepositoryImpl
import com.apui.iiscassignment.domain.repository.AuthRepository
import com.apui.iiscassignment.ui.auth.signin.GoogleSignInViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    viewModel { (context: Context) -> GoogleSignInViewModel(get(), context) }
}