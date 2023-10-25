package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.profile

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.AppRemember
import hu.bme.aut.android.securityapp.constants.DataFieldErrors
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.constants.sha256
import hu.bme.aut.android.securityapp.constants.validatePasswordsMatch
import hu.bme.aut.android.securityapp.constants.validateUserEmail
import hu.bme.aut.android.securityapp.constants.validateUserFullName
import hu.bme.aut.android.securityapp.constants.validateUserNickname
import hu.bme.aut.android.securityapp.constants.validateUserPassword
import hu.bme.aut.android.securityapp.constants.validateUserUsername
import hu.bme.aut.android.securityapp.data.model.people.PersonDefault
import hu.bme.aut.android.securityapp.data.repository.PersonRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val personRepository: PersonRepository,
    private val sharedPreferences: SharedPreferences,
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _userData = MutableStateFlow<PersonDefault>(PersonDefault())
    val userData = _userData.asStateFlow()

    private var _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri = _imageUri.asStateFlow()

    private val _passwordChangeData = MutableStateFlow<PasswordChangeData>(PasswordChangeData())
    val passwordChangeData = _passwordChangeData.asStateFlow()

    private val _dataFieldErrors = MutableStateFlow<Set<DataFieldErrors>>(setOf())
    val dataFieldErrors = _dataFieldErrors.asStateFlow()

    init {
        loadData()
    }

    fun evoke(action: ProfileAction){
        when(action){
            ProfileAction.LogOut -> {
                logOut()
            }
            is ProfileAction.SetUserData -> {
                _userData.value = action.userData
            }
            is ProfileAction.UpdatePerson -> {

                if(_dataFieldErrors.value.isNotEmpty() && _screenState.value !is ScreenState.Error){
                    _screenState.value = ScreenState.Error(message = _dataFieldErrors.value.first().message)
                }

                if(_dataFieldErrors.value.isEmpty()){
                    if(_passwordChangeData.value.password.isNotEmpty()){
                        _userData.update {
                            it.copy(
                                password = _passwordChangeData.value.password.sha256()
                            )
                        }
                    }

                    updatePerson(action.context)
                }
            }
            is ProfileAction.SetUri -> {
                _imageUri.value = action.uri
            }
            is ProfileAction.PasswordsChange -> {
                _passwordChangeData.update {
                    it.copy(
                        password = action.password,
                        passwordChange = action.passwordChange,
                    )
                }
            }
        }

        validateFields()
    }

    private fun loadData(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = personRepository.getPerson(LoggedPerson.ID)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()

                    with(result.data!!){
                        _userData.update { userData ->
                            userData.copy(
                                fullName = fullName,
                                username = username,
                                nickname = nickname,
                                email = email,
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun updatePerson(context: Context){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = personRepository.updatePerson(personId = LoggedPerson.ID, person = _userData.value)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success(message = result.message!!, show = true)

                    AppRemember.rememberLoginData(
                        sharedPreferences = sharedPreferences,
                        username = _userData.value.username,
                        password = _passwordChangeData.value.password
                    )

                    _userData.value = result.data!!
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }

        uploadProfilePicture(context = context)
    }

    private fun uploadProfilePicture(context: Context){
        _imageUri.value ?: return

        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = personRepository.uploadProfilePicture(personId = LoggedPerson.ID, imageUri = _imageUri.value!!, context = context)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success(message = result.message!!, show = true)
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun logOut(){
        LoggedPerson.ID = 0
        LoggedPerson.CURRENT_JOB_ID = 0

        with(AppRemember){
            forgetSelectedJobId(sharedPreferences = sharedPreferences)
            forgetLoginData(sharedPreferences = sharedPreferences)
        }
    }

    private fun validateFields(){
        val errors = mutableSetOf<DataFieldErrors>()

        errors.add(validateUserFullName(fullname = _userData.value.fullName))
        errors.add(validateUserUsername(username = _userData.value.username))
        errors.add(validateUserNickname(nickname = _userData.value.nickname))
        errors.add(validateUserEmail(email = _userData.value.email))

        if(_passwordChangeData.value.password.isNotEmpty() || _passwordChangeData.value.passwordChange.isNotEmpty()) {
            errors.add(validateUserPassword(password = _passwordChangeData.value.password))
            errors.add(validateUserPassword(password = _passwordChangeData.value.passwordChange))
            errors.add(
                validatePasswordsMatch(
                    password = _passwordChangeData.value.password,
                    passwordChange = _passwordChangeData.value.passwordChange
                )
            )
        }

        errors.removeIf { error ->
            error is DataFieldErrors.NoError
        }

        _dataFieldErrors.value = errors
    }
}

data class PasswordChangeData(
    val password: String = "",
    val passwordChange: String = "",
)

sealed class ProfileAction{
    object LogOut : ProfileAction()
    class UpdatePerson(val context: Context) : ProfileAction()
    class SetUserData(val userData: PersonDefault) : ProfileAction()
    class SetUri(val uri: Uri) : ProfileAction()
    class PasswordsChange(val password: String, val passwordChange: String) : ProfileAction()
}