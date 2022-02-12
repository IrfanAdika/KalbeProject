package com.kalbe.kalbeproject.module.product.di

import com.kalbe.kalbeproject.module.product.viewmodel.ProductViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object ProductModule {

    fun load() {
        loadKoinModules(productModule)
    }

    private val productModule = module {
        viewModel {
            ProductViewModel(get())
        }
    }
}