package com.kalbe.datasource.dependencies

import android.app.Application
import android.content.SharedPreferences
import at.favre.lib.armadillo.Armadillo
import com.kalbe.datasource.local.AuthManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val PreferenceModule = module {
    single(named("auth_preference")) {
        provideAuthPreference(
            androidApplication()
        )
    }
    factory { AuthManager(get(named("auth_preference"))) }
}

private const val PREFERENCE_AUTH = "com.yummy.auth_pref"

private fun provideAuthPreference(app: Application): SharedPreferences =
    Armadillo.create(app, PREFERENCE_AUTH)
        .encryptionFingerprint(app)
        .build()