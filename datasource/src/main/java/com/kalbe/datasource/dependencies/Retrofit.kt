package com.kalbe.datasource.dependencies

import android.util.Log.VERBOSE
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.kalbe.core.BuildConfig
import com.kalbe.datasource.remote.AuthInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitModule {

    val retrofitModule = module {
        factory { AuthInterceptor(get()) }
        factory { provideOkHttpClient(get(), get()) }
        factory { provideLoggingInterceptor() }
        single { provideRetrofit(get()) }
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun provideOkHttpClient(authInterceptor: AuthInterceptor, loggingInterceptor: LoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(loggingInterceptor)
        }

        okHttpClient.apply {
            addInterceptor(authInterceptor)
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }

        return okHttpClient.build()
    }

    /**
     * Login Networking
     */
    private fun provideLoggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .setLevel(Level.BASIC)
            .log(VERBOSE)
            .tag("Logging Network")
            .request("Request API")
            .response("Response API")
            .build()
    }
}
