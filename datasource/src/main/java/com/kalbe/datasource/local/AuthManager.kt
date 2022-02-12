package com.kalbe.datasource.local

import android.content.SharedPreferences
import com.kalbe.core.utils.Constants
import com.kalbe.datasource.model.AuthUser

/**
 * Auth manager for save data user auth
 * encode and decode data
 */
class AuthManager(private val preferences: SharedPreferences) {

    /**
     * Save all auth
     */
    fun saveAllAuth(data: AuthUser) {
        preferences.edit()
            .putString(Constants.INITIAL_NAME, data.initialName)
            .putString(Constants.AUTH_TOKEN, data.token)
            .putString(Constants.AUTH_REFRESH_TOKEN, data.refreshToken)
            .apply()
    }

    /**
     * Remove auth data
     */
    fun removeAllAuth() {
        preferences.edit()
            .clear()
            .apply()
    }

    /**
     * Get token only
     */
    fun getToken(): String {
        getAuth()?.token.let {
            return if (it.isNullOrEmpty()) {
                getAuth()?.refreshToken ?: ""
            } else {
                it
            }
        }
    }

    /**
     * Get all auth
     */
    fun getAuth(): AuthUser? {
        val data = AuthUser()
        data.initialName = preferences.getString(Constants.INITIAL_NAME, "") ?: ""
        data.token = preferences.getString(Constants.AUTH_TOKEN, "") ?: ""
        data.refreshToken = preferences.getString(Constants.AUTH_REFRESH_TOKEN, "") ?: ""
        return data
    }
}
