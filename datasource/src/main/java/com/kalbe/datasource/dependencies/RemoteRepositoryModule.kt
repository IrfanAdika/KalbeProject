package com.kalbe.datasource.dependencies

import com.kalbe.datasource.remote.ApiRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory {
        ApiRepository(get())
    }
}