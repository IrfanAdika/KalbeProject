package com.kalbe.datasource.local

import android.content.SharedPreferences
import com.kalbe.core.utils.Constants
import com.kalbe.datasource.model.Auth

/**
 * Auth manager for save data user auth
 * encode and decode data
 */
class AuthManager(private val preferences: SharedPreferences) {

    /**
     * Save all auth
     */
    fun saveToken(token: String) {
        preferences.edit()
            .putString(Constants.AUTH_TOKEN, token)
            .apply()
    }

    /**
     * Remove auth data
     */
    fun removeToken() {
        preferences.edit()
            .clear()
            .apply()
    }

    /**
     * Get all auth
     */
    fun getToken(): String? {
        val data = Auth()
        return preferences.getString(Constants.AUTH_TOKEN, "") ?: ""
    }
}
