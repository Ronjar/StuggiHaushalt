package com.robingebert.stuggihaushalt.feature_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robingebert.stuggihaushalt.data.KtorClient
import kotlinx.coroutines.launch

class LoginViewModel(private val client: KtorClient): ViewModel() {

    fun login(username: String, password: String){
        viewModelScope.launch {
            client.login(username, password) {
                it.onSuccess {

                }.onFailure {

                }
            }
        }
    }
}