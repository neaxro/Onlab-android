package hu.bme.aut.android.securityapp.ui.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _errors = MutableStateFlow<FieldErrors>(FieldErrors())
    val errors = _errors.asStateFlow()

    fun register(onSuccess: () -> Unit, onError: (String) -> Unit){
        //val registerData = PersonDefault(email.value, fullName.value, nickname.value, password.value, username.value)


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
        val fullNameCheck = validateFullname(_person.value.fullName)
        val userNameCheck = validateUsername(_person.value.username)
        val nickNameCheck = validateNickname(_person.value.nickname)
        val emailAddressCheck = validateEmail(_person.value.email)
        val passwordCheck = validatePassword(_person.value.password)
        val passwordMach = if(_person.value.password == _rePassword.value) RegisterValueError.NoError else RegisterValueError.PasswordMissmachError("Passwords do not mach!")

        _errors.update {
            it.copy(
                fullName = fullNameCheck != RegisterValueError.NoError,
                userName = userNameCheck != RegisterValueError.NoError,
                nickName = nickNameCheck != RegisterValueError.NoError,
                emailAddress = emailAddressCheck != RegisterValueError.NoError,
                password = passwordCheck != RegisterValueError.NoError,
                passwordMissmach = passwordMach != RegisterValueError.NoError
            )
        }
    }
}

private fun validateFullname(fullname: String): RegisterValueError{
    if(fullname.isEmpty()) return RegisterValueError.FullNameError("Fullname is empty!")
    if(fullname.length > 30) return RegisterValueError.FullNameError("Fullname is too long!")

    return RegisterValueError.NoError
}

private fun validateUsername(username: String): RegisterValueError{
    if(!username.matches(Regex("\\S+"))) return RegisterValueError.UserNameError("Username must not contain whitespaces!")

    return RegisterValueError.NoError
}

private fun validateNickname(nickname: String): RegisterValueError{
    if(!nickname.matches(Regex("\\S+"))) return RegisterValueError.NickNameError("Nickname must not contain whitespaces!")
    if(!nickname.matches(Regex("^[a-zA-Z]+\$"))) return RegisterValueError.NickNameError("Nickname must contains only letters!")

    return RegisterValueError.NoError
}

private fun validateEmail(email: String): RegisterValueError{
    if(!email.matches(Regex("^[\\w-]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))) return RegisterValueError.EmailAddressError("Invalid email address format!")

    return RegisterValueError.NoError
}

private fun validatePassword(password: String): RegisterValueError{
    if(password.length < 8) return RegisterValueError.PasswordError("Password is too short!")
    if(password.length > 30) return RegisterValueError.PasswordError("Password is too long!")

    return RegisterValueError.NoError
}

sealed class RegisterEvent{
    class ChangeFullName(val fullName: String) : RegisterEvent()
    class ChangeUserName(val userName: String) : RegisterEvent()
    class ChangeNickName(val nickName: String) : RegisterEvent()
    class ChangeEmailAddress(val emailAddress: String) : RegisterEvent()
    class ChangePassword(val password: String) : RegisterEvent()
    class ChangeRePassword(val rePassword: String) : RegisterEvent()
}

sealed class RegisterValueError(val message: String){
    object NoError: RegisterValueError("")
    class FullNameError(val msg: String): RegisterValueError(message = msg)
    class UserNameError(val msg: String): RegisterValueError(message = msg)
    class NickNameError(val msg: String): RegisterValueError(message = msg)
    class EmailAddressError(val msg: String): RegisterValueError(message = msg)
    class PasswordError(val msg: String): RegisterValueError(message = msg)
    class PasswordMissmachError(val msg: String): RegisterValueError(message = msg)
}

data class FieldErrors(
    val fullName: Boolean = true,
    val userName: Boolean = false,
    val nickName: Boolean = false,
    val emailAddress: Boolean = false,
    val password: Boolean = false,
    val passwordMissmach: Boolean = false,
)

fun FieldErrors.isError(): Boolean{
    val isError = this.fullName
            || this.userName
            || this.nickName
            || this.emailAddress
            || this.password
            || this.passwordMissmach

    return isError
}