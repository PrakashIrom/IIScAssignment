package com.apui.iiscassignment.di

import android.content.Context
import com.apui.iiscassignment.data.repository.GmailAuthRepoImpl
import com.apui.iiscassignment.domain.repository.GmailAuthRepo
import com.apui.iiscassignment.ui.auth.authscreen.GmailAuthViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<GmailAuthRepo> { GmailAuthRepoImpl(get()) }
    viewModel { (context: Context) ->
        GmailAuthViewModel(get(), context)
    }
}