package hu.bme.aut.android.securityapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.DataFieldErrors
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.constants.validateUserPassword
import hu.bme.aut.android.securityapp.constants.validateUserUsername
import hu.bme.aut.android.securityapp.data.model.people.LoginData
import hu.bme.aut.android.securityapp.data.repository.LoginRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _loginData = MutableStateFlow<LoginData>(LoginData(username = "nemesa", password = "Asdasd11"))
    val loginData = _loginData.asStateFlow()

    private val _errors = MutableStateFlow<LoginFieldErrors>(LoginFieldErrors())
    val errors = _errors.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.NotLoggedIn)
    val loginState = _loginState.asStateFlow()

    private fun login(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginRepository.loginPerson(loginData = _loginData.value)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _loginState.value = LoginState.LoggedIn

                    LoggedPerson.ID = result.data!!.id
                    LoggedPerson.TOKEN = result.data.token
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                    _loginState.value = LoginState.NotLoggedIn
                }
            }
        }
    }

    fun evoke(action: LoginAction){
        when(action){
            is LoginAction.Login -> {
                login()
            }

            is LoginAction.UpdateUsername -> {
                _loginData.update {
                    it.copy(
                        username = action.username.trim()
                    )
                }

                _errors.update {
                    it.copy(
                        userName = validateUserUsername(action.username.trim()) != DataFieldErrors.NoError
                    )
                }
            }

            is LoginAction.UpdatePassword -> {
                _loginData.update {
                    it.copy(
                        password = action.password.trim()
                    )
                }

                _errors.update {
                    it.copy(
                        password = validateUserPassword(action.password.trim()) != DataFieldErrors.NoError
                    )
                }
            }

            else -> {}
        }
    }
}

sealed class LoginAction{
    object Login : LoginAction()
    class UpdateUsername(val username: String) : LoginAction()
    class UpdatePassword(val password: String) : LoginAction()
}

sealed class LoginState{
    object LoggedIn : LoginState()
    object NotLoggedIn : LoginState()
}

data class LoginFieldErrors(
    val userName: Boolean = true,
    val password: Boolean = false,
)