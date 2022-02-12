package com.kalbe.kalbeproject.module.auth.di

import com.kalbe.kalbeproject.module.auth.viewmodel.AuthViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object AuthModule {

    fun load() {
        loadKoinModules(authModule)
    }

    private val authModule = module {
        viewModel {
            AuthViewModel(get(), get())
        }
    }
}