package hu.bme.aut.android.securityapp.feature.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.data.model.people.RegisterData
import hu.bme.aut.android.securityapp.domain.repository.RegisterRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
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

    var registrationState = mutableStateOf("")

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