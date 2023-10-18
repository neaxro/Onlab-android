package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
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
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private var _userData = MutableStateFlow<PersonDefault>(PersonDefault())
    val userData = _userData.asStateFlow()

    private var _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri = _imageUri.asStateFlow()

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
                updatePerson()
                uploadProfilePicture(action.context)
            }
            is ProfileAction.SetUri -> {
                _imageUri.value = action.uri
            }
        }
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

    private fun updatePerson(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = personRepository.updatePerson(personId = LoggedPerson.ID, person = _userData.value)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _userData.value = result.data!!
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun uploadProfilePicture(context: Context){
        _imageUri.value ?: return

        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = personRepository.uploadProfilePicture(personId = LoggedPerson.ID, imageUri = _imageUri.value!!, context = context)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
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
    }
}

sealed class ProfileAction{
    object LogOut : ProfileAction()
    class UpdatePerson(val context: Context) : ProfileAction()
    class SetUserData(val userData: PersonDefault) : ProfileAction()
    class SetUri(val uri: Uri) : ProfileAction()
}