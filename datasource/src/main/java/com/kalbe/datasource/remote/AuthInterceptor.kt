package com.kalbe.datasource.remote

import com.kalbe.datasource.local.AuthManager
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor to add auth token to requests
 */
class AuthInterceptor(private val authManager: AuthManager): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", "Bearer ${authManager.getToken()}")


        return chain.proceed(requestBuilder.build())
    }
}