package hu.bme.aut.android.securityapp.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.data.model.Person
import hu.bme.aut.android.securityapp.domain.repository.LoginRepository
import javax.inject.Inject
import hu.bme.aut.android.securityapp.data.model.LoginData
import hu.bme.aut.android.securityapp.data.model.RegisterData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
): ViewModel() {

    var _username = mutableStateOf("")
    var _password = mutableStateOf("")

    fun LoginUser(onSuccess: ()->Unit, onError: ()->Unit){
        // TODO: Validate username
        // TODO: Validta password

        val loginData = LoginData(_username.value, _password.value)

        viewModelScope.launch(Dispatchers.IO) {
            val loginToken: String = repository.loginPerson(loginData)
            if(loginToken.isNotEmpty()){
                onSuccess()
            }
        }

        onError()
    }
    fun usernameChanged(value: String): Unit{
        _username.value = value
    }
    fun passwordChanged(value: String): Unit{
        _password.value = value
    }
}