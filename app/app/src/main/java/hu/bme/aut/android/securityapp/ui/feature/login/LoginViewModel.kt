package hu.bme.aut.android.securityapp.ui.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.AppRemember
import hu.bme.aut.android.securityapp.constants.DataFieldErrors
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.constants.sha256
import hu.bme.aut.android.securityapp.constants.validateUserPassword
import hu.bme.aut.android.securityapp.constants.validateUserUsername
import hu.bme.aut.android.securityapp.data.model.people.LoginData
import hu.bme.aut.android.securityapp.data.repository.JobRepository
import hu.bme.aut.android.securityapp.data.repository.LoginRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val jobRepository: JobRepository,
    private val sharedPreferences: SharedPreferences,
    private val applicationContext: Context
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _loginData = MutableStateFlow<LoginData>(LoginData(username = "nemesa", password = "Asdasd11"))
    val loginData = _loginData.asStateFlow()

    private val _errors = MutableStateFlow<LoginFieldErrors>(LoginFieldErrors())
    val errors = _errors.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.NotLoggedIn)
    val loginState = _loginState.asStateFlow()

    init {
        val rememberedLoginData = AppRemember.getLoginData(sharedPreferences = sharedPreferences)
        if(rememberedLoginData != null){
            _loginData.value = rememberedLoginData
            login()
        }
    }

    private fun login(){
        _screenState.value = ScreenState.Loading()

        val hashedLoginData = _loginData.value.copy(
            password = _loginData.value.password.sha256()
        )

        viewModelScope.launch(Dispatchers.IO) {
            val result = loginRepository.loginPerson(loginData = hashedLoginData)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _loginState.value = LoginState.LoggedIn

                    LoggedPerson.ID = result.data!!.id
                    LoggedPerson.TOKEN = result.data.token

                    AppRemember.rememberLoginData(
                        sharedPreferences = sharedPreferences,
                        username = _loginData.value.username,
                        password = _loginData.value.password,
                    )

                    loadAndSelectRememberedJobId()
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
                        userName = validateUserUsername(action.username.trim(), applicationContext) != DataFieldErrors.NoError
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
                        password = validateUserPassword(action.password.trim(), applicationContext) != DataFieldErrors.NoError
                    )
                }
            }

            else -> {}
        }
    }

    private fun loadAndSelectRememberedJobId(){
        val rememberedJobId = AppRemember.getSelectedJobId(sharedPreferences = sharedPreferences)

        if(rememberedJobId > 0){
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                val result = jobRepository.selectJob(jobId = rememberedJobId, personId = LoggedPerson.ID)
                when(result){
                    is Resource.Error -> {}
                    is Resource.Success -> {
                        LoggedPerson.CURRENT_JOB_ID = rememberedJobId
                        LoggedPerson.TOKEN = result.data!!.token
                    }
                }
            }
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