package hu.bme.aut.android.securityapp.feature.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.data.model.RegisterData
import hu.bme.aut.android.securityapp.domain.repository.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
): ViewModel() {
    var email = mutableStateOf("")
    var fullName = mutableStateOf("")
    var nickname = mutableStateOf("")
    var password = mutableStateOf("")
    var passwordAgain = mutableStateOf("")
    var username = mutableStateOf("")

    fun changeEmail(value: String): Unit{
        email.value = value
    }
    fun changeFullName(value: String): Unit{
        fullName.value = value
    }
    fun changeNickname(value: String): Unit{
        nickname.value = value
    }
    fun changePassword(value: String): Unit{
        password.value = value
    }
    fun changePasswordAgain(value: String): Unit{
        passwordAgain.value = value
    }
    fun changeUsername(value: String): Unit{
        username.value = value
    }

    fun register(onSuccess: ()->Unit, onError: ()->Unit): Unit{
        val registerData = RegisterData(email.value, fullName.value, nickname.value, password.value, username.value)

        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.registerUser(registerData)

            if(result.isNotEmpty()){
                onSuccess()
            }
        }

        onError()
    }
}