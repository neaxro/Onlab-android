package hu.bme.aut.android.securityapp.ui.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.DataFieldErrors
import hu.bme.aut.android.securityapp.constants.validateUserEmail
import hu.bme.aut.android.securityapp.constants.validateUserFullName
import hu.bme.aut.android.securityapp.constants.validateUserNickname
import hu.bme.aut.android.securityapp.constants.validateUserPassword
import hu.bme.aut.android.securityapp.constants.validateUserUsername
import hu.bme.aut.android.securityapp.data.model.people.PersonDefault
import hu.bme.aut.android.securityapp.data.repository.RegisterRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
): ViewModel() {

    private val _person = MutableStateFlow<PersonDefault>(PersonDefault())
    val person = _person.asStateFlow()

    private val _rePassword = MutableStateFlow<String>("")
    val rePassword = _rePassword.asStateFlow()

    private val _errors = MutableStateFlow<RegisterFieldErrors>(RegisterFieldErrors())
    val errors = _errors.asStateFlow()

    fun register(onSuccess: () -> Unit, onError: (String) -> Unit){

        viewModelScope.launch(Dispatchers.IO) {
            val result: Resource<PersonDefault> = repository.registerUser(_person.value)

            when(result){
                is Resource.Success<PersonDefault> -> {
                    withContext(Dispatchers.Main){
                        onSuccess()
                    }
                }
                is Resource.Error<PersonDefault> -> {
                    withContext(Dispatchers.Main){
                        onError(result.message!!)
                    }
                }
            }
        }
    }

    fun onEvoke(event: RegisterEvent){
        when(event){
            is RegisterEvent.ChangeFullName -> {
                _person.update {
                    it.copy(
                        fullName = event.fullName
                    )
                }
            }

            is RegisterEvent.ChangeUserName -> {
                _person.update {
                    it.copy(
                        username = event.userName
                    )
                }
            }

            is RegisterEvent.ChangeNickName -> {
                _person.update {
                    it.copy(
                        nickname = event.nickName
                    )
                }
            }

            is RegisterEvent.ChangeEmailAddress -> {
                _person.update {
                    it.copy(
                        email = event.emailAddress
                    )
                }
            }

            is RegisterEvent.ChangePassword -> {
                _person.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            is RegisterEvent.ChangeRePassword -> {
                _rePassword.value = event.rePassword
            }
        }

        checkAllValues()
    }

    private fun checkAllValues(){
        val fullNameCheck = validateUserFullName(_person.value.fullName)
        val userNameCheck = validateUserUsername(_person.value.username)
        val nickNameCheck = validateUserNickname(_person.value.nickname)
        val emailAddressCheck = validateUserEmail(_person.value.email)
        val passwordCheck = validateUserPassword(_person.value.password)
        val passwordMach = if(_person.value.password == _rePassword.value) DataFieldErrors.NoError else DataFieldErrors.PasswordMissmachError("Passwords do not mach!")

        _errors.update {
            it.copy(
                fullName = fullNameCheck != DataFieldErrors.NoError,
                userName = userNameCheck != DataFieldErrors.NoError,
                nickName = nickNameCheck != DataFieldErrors.NoError,
                emailAddress = emailAddressCheck != DataFieldErrors.NoError,
                password = passwordCheck != DataFieldErrors.NoError,
                passwordMissmach = passwordMach != DataFieldErrors.NoError
            )
        }
    }
}

sealed class RegisterEvent{
    class ChangeFullName(val fullName: String) : RegisterEvent()
    class ChangeUserName(val userName: String) : RegisterEvent()
    class ChangeNickName(val nickName: String) : RegisterEvent()
    class ChangeEmailAddress(val emailAddress: String) : RegisterEvent()
    class ChangePassword(val password: String) : RegisterEvent()
    class ChangeRePassword(val rePassword: String) : RegisterEvent()
}

data class RegisterFieldErrors(
    val fullName: Boolean = true,
    val userName: Boolean = false,
    val nickName: Boolean = false,
    val emailAddress: Boolean = false,
    val password: Boolean = false,
    val passwordMissmach: Boolean = false,
)

fun RegisterFieldErrors.isError(): Boolean{
    val isError = this.fullName
            || this.userName
            || this.nickName
            || this.emailAddress
            || this.password
            || this.passwordMissmach

    return isError
}