package com.kalbe.kalbeproject.main

import com.kalbe.core.application.CoreApp
import com.kalbe.datasource.dependencies.PreferenceModule
import com.kalbe.datasource.dependencies.RetrofitModule
import com.kalbe.datasource.dependencies.repositoryModule
import com.kalbe.datasource.dependencies.serviceModule
import org.koin.core.module.Module

class App: CoreApp() {

    override fun getDataModules() = arrayOf(serviceModule, RetrofitModule.retrofitModule, PreferenceModule, repositoryModule,)
}