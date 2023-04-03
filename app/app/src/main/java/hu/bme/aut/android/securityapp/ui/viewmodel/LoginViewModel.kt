package hu.bme.aut.android.securityapp.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.data.model.Person
import hu.bme.aut.android.securityapp.domain.repository.LoginRepository
import retrofit2.Response
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import hu.bme.aut.android.securityapp.data.model.LoginData
import hu.bme.aut.android.securityapp.data.model.RegisterData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class LoginInformation(
    val username: String = "",
    val password: String = ""
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
): ViewModel() {

    private val state = MutableStateFlow(LoginInformation())
    val uiState: StateFlow<LoginInformation> = state.asStateFlow()

    fun LoginUser(loginData: LoginData): Person?{
        return runBlocking {
            repository.LoginPerson(loginData)
        }
    }

    fun RegisterUser(registerData: RegisterData): Person?{
        return runBlocking{
            repository.RegisterPerson(registerData)
        }
    }
}