package com.kalbe.kalbeproject.module.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.kalbe.datasource.local.AuthManager
import com.kalbe.datasource.model.Auth
import com.kalbe.datasource.model.Result
import com.kalbe.datasource.model.User
import com.kalbe.datasource.remote.ApiRepository
import com.kalbe.datasource.remote.Resource
import kotlinx.coroutines.launch

class AuthViewModel(private val apiRepository: ApiRepository, private val authManager: AuthManager): ViewModel() {

    private var _registerFormResult = MutableLiveData<Result<User>>()
    val registerFormResult: LiveData<Result<User>> get() = _registerFormResult

    fun register(email: String, password: String) = viewModelScope.launch {
        val body = JsonObject()
        body.addProperty("email", email)
        body.addProperty("password", password)

        when (val response = apiRepository.register(body = body)) {
            is Resource.Success -> {
                val auth = response.value.data
                _registerFormResult.value = Result.Success(value = auth)
            }

            is Resource.Failure -> {
                _registerFormResult.value = Result.Failure(message = response.errorMessage)
            }
        }
    }

    private var _loginFormResult = MutableLiveData<Result<Auth>>()
    val loginFormResult: LiveData<Result<Auth>> get() = _loginFormResult

    fun login(email: String, password: String) = viewModelScope.launch {
        val body = JsonObject()
        body.addProperty("email", email)
        body.addProperty("password", password)

        when (val response = apiRepository.login(body = body)) {
            is Resource.Success -> {
                val user = response.value
                authManager.saveToken(token = user.token)
                _loginFormResult.value = Result.Success(value = user)
            }

            is Resource.Failure -> {
                _loginFormResult.value = Result.Failure(message = response.errorMessage)
            }
        }
    }

    private var _isEnableButton = MutableLiveData<Boolean>()
    val isEnableButton: LiveData<Boolean> get() = _isEnableButton

    fun checkField(email: String, password: String) {
        _isEnableButton.value = !(email.isEmpty() || password.isEmpty())
    }
}