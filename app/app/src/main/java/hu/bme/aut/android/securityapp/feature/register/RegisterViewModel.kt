package hu.bme.aut.android.securityapp.feature.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.data.model.RegisterData
import hu.bme.aut.android.securityapp.domain.repository.RegisterRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RegisterValidationMessage(val errorMessage: String){
    class NoError(): RegisterValidationMessage("No error")
    class PasswordValidationMessage(errorMessage: String): RegisterValidationMessage(errorMessage)
    class FullnameValidationMessage(errorMessage: String): RegisterValidationMessage(errorMessage)
    class UsernameValidationMessage(errorMessage: String): RegisterValidationMessage(errorMessage)
    class EmailValidationMessage(errorMessage: String): RegisterValidationMessage(errorMessage)
}

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

    var registrationState = mutableStateOf("")
    var passwordsCorrect = mutableStateOf(true)

    fun checkPassword(): RegisterValidationMessage{
        if(password.value == passwordAgain.value){
            return RegisterValidationMessage.PasswordValidationMessage("Passwords do not match!")
        }

        if(password.value.length < 6){
            return RegisterValidationMessage.PasswordValidationMessage("Password is too short!")
        }

        if(password.value.length < 8){
            return RegisterValidationMessage.PasswordValidationMessage("Password min length is 8 characters!")
        }

        if(password.value.length > 30){
            return RegisterValidationMessage.PasswordValidationMessage("Password max length is 32 characters!")
        }

        val regexPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,30}\$".toRegex()
        if(!regexPattern.matches(password.value)){
            return RegisterValidationMessage.PasswordValidationMessage("Must contain uppercase letter and number!")
        }

        return RegisterValidationMessage.NoError()
    }

    fun register(){
        val registerData = RegisterData(email.value, fullName.value, nickname.value, password.value, username.value)

        viewModelScope.launch(Dispatchers.IO) {
            val result: Resource<RegisterData> = repository.registerUser(registerData)

            when(result){
                is Resource.Success<RegisterData> -> {
                    registrationState.value = "Success"
                }
                is Resource.Error<RegisterData> -> {
                    registrationState.value = result.message!!
                }
            }
        }
    }
}