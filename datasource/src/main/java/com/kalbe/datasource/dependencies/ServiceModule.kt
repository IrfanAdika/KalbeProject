package com.kalbe.datasource.dependencies

import com.kalbe.datasource.remote.Services
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(Services::class.java) }
}