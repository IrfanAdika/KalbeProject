package com.kalbe.core.dependencies

import com.kalbe.core.connection.NetworkConnection
import com.kalbe.core.utils.BroadcastReceivers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val androidModule = module {
    single {
        NetworkConnection(androidApplication())
    }

    single {
        BroadcastReceivers(androidApplication())
    }
}