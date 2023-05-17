package hu.bme.aut.android.securityapp.ui.feature.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.data.model.people.PersonDetail
import hu.bme.aut.android.securityapp.data.repository.RegisterRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    fun register(onSuccess: () -> Unit, onError: (String) -> Unit){
        val registerData = PersonDetail(email.value, fullName.value, nickname.value, password.value, username.value)

        viewModelScope.launch(Dispatchers.IO) {
            val result: Resource<PersonDetail> = repository.registerUser(registerData)

            when(result){
                is Resource.Success<PersonDetail> -> {
                    withContext(Dispatchers.Main){
                        onSuccess()
                    }
                }
                is Resource.Error<PersonDetail> -> {
                    withContext(Dispatchers.Main){
                        onError(result.message!!)
                    }
                }
            }
        }
    }
}