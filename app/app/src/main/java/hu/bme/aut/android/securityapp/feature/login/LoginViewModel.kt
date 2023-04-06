package hu.bme.aut.android.securityapp.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.domain.repository.LoginRepository
import javax.inject.Inject
import hu.bme.aut.android.securityapp.data.model.LoginData
import hu.bme.aut.android.securityapp.data.model.LoginResponse
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
): ViewModel() {

    var username = mutableStateOf("")
    var password = mutableStateOf("")

    var login = mutableStateOf("")

    fun LoginUser(){
        if(username.value.isEmpty() || password.value.isEmpty()) return

        val loginData = LoginData(username.value.trim(), password.value.trim())
        viewModelScope.launch(Dispatchers.IO) {
            val loginResult: Resource<LoginResponse> = repository.loginPerson(loginData)

            when(loginResult){
                is Resource.Success<LoginResponse> -> {
                    LoggedPerson.ID = loginResult.data!!.id
                    LoggedPerson.TOKEN = loginResult.data!!.token

                    login.value = "Success! ID: ${LoggedPerson.ID}"
                }
                is Resource.Error<LoginResponse> -> {
                    login.value = loginResult.message!!
                }
            }
        }
    }
}