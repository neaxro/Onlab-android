package hu.bme.aut.android.securityapp.ui.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.DataFieldErrors
import hu.bme.aut.android.securityapp.constants.sha256
import hu.bme.aut.android.securityapp.constants.validateUserEmail
import hu.bme.aut.android.securityapp.constants.validateUserFullName
import hu.bme.aut.android.securityapp.constants.validateUserNickname
import hu.bme.aut.android.securityapp.constants.validateUserPassword
import hu.bme.aut.android.securityapp.constants.validateUserUsername
import hu.bme.aut.android.securityapp.data.model.people.PersonDefault
import hu.bme.aut.android.securityapp.data.repository.RegisterRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _person = MutableStateFlow<PersonDefault>(PersonDefault())
    val person = _person.asStateFlow()

    private val _rePassword = MutableStateFlow<String>("")
    val rePassword = _rePassword.asStateFlow()

    private val _errors = MutableStateFlow<RegisterFieldErrors>(RegisterFieldErrors())
    val errors = _errors.asStateFlow()

    private fun register(){
        _screenState.value = ScreenState.Loading()

        val hashedRegisterData = _person.value.copy(
            password = _person.value.password.sha256()
        )

        viewModelScope.launch(Dispatchers.IO) {
            val result: Resource<PersonDefault> = registerRepository.registerUser(registerData = hashedRegisterData)

            when(result){
                is Resource.Success<PersonDefault> -> {
                    _screenState.value = ScreenState.Success(message = result.message!!, show = true)
                }
                is Resource.Error<PersonDefault> -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    fun evoke(action: RegisterAction){
        when(action){
            is RegisterAction.ChangeFullName -> {
                _person.update {
                    it.copy(
                        fullName = action.fullName
                    )
                }
            }

            is RegisterAction.ChangeUserName -> {
                _person.update {
                    it.copy(
                        username = action.userName
                    )
                }
            }

            is RegisterAction.ChangeNickName -> {
                _person.update {
                    it.copy(
                        nickname = action.nickName
                    )
                }
            }

            is RegisterAction.ChangeEmailAddress -> {
                _person.update {
                    it.copy(
                        email = action.emailAddress
                    )
                }
            }

            is RegisterAction.ChangePassword -> {
                _person.update {
                    it.copy(
                        password = action.password
                    )
                }
            }

            is RegisterAction.ChangeRePassword -> {
                _rePassword.value = action.rePassword
            }

            RegisterAction.Register -> {
                register()
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
                passwordMismatch = passwordMach != DataFieldErrors.NoError
            )
        }
    }
}

sealed class RegisterAction{
    class ChangeFullName(val fullName: String) : RegisterAction()
    class ChangeUserName(val userName: String) : RegisterAction()
    class ChangeNickName(val nickName: String) : RegisterAction()
    class ChangeEmailAddress(val emailAddress: String) : RegisterAction()
    class ChangePassword(val password: String) : RegisterAction()
    class ChangeRePassword(val rePassword: String) : RegisterAction()
    object Register : RegisterAction()
}

data class RegisterFieldErrors(
    val fullName: Boolean = true,
    val userName: Boolean = false,
    val nickName: Boolean = false,
    val emailAddress: Boolean = false,
    val password: Boolean = false,
    val passwordMismatch: Boolean = false,
)

fun RegisterFieldErrors.isError(): Boolean {
    return (fullName
            || userName
            || nickName
            || emailAddress
            || password
            || passwordMismatch)
}