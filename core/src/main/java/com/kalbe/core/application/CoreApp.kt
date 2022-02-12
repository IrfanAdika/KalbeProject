package com.kalbe.core.application

import android.app.Application
import com.facebook.stetho.Stetho
import com.kalbe.core.BuildConfig
import com.kalbe.core.dependencies.androidModule
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

abstract class CoreApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initRealm()
        initialStetho()
        initialKoin()
    }

    private fun initRealm() {
        /**
         * Create Realm database with name
         * Realm database is local database
         * For saving data in local storage
         */
        Realm.init(this)
        val realm = RealmConfiguration.Builder()
        realm.name("self_ordering.realm")
        realm.deleteRealmIfMigrationNeeded()
        Realm.setDefaultConfiguration(realm.build())
    }

    /**
     * Debugging
     */
    private fun initialStetho() {
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }

    /**
     * Init Dependencies Injection
     */
    private fun initialKoin() {
        startKoin {

            // Logger for android
            androidLogger(Level.ERROR)

            // Set the Android Context
            androidContext(this@CoreApp)

            modules(listOf(*defaultModules(), *getDataModules()))
        }
    }

    private fun defaultModules(): Array<Module> =
        arrayOf(androidModule)

    /**
     * Return the modules for Data
     */
    abstract fun getDataModules(): Array<Module>
}