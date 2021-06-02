package com.betterfly.prueba.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.betterfly.prueba.login.model.LoginInteractor

class LoginViewModel: ViewModel() {
    private val result = MutableLiveData<Boolean>()
    private val interactor: LoginInteractor = LoginInteractor()

    fun isLogin(): LiveData<Boolean> {
        return result
    }

    fun login(email: String, password: String) {
        interactor.login(email, password) { login ->
            result.value = login
        }
    }

    fun create(email: String, password: String) {
        interactor.create(email, password) { user ->
            result.value = user
        }
    }
}