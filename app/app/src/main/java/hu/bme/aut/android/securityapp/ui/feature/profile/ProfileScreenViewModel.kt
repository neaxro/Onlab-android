package hu.bme.aut.android.securityapp.ui.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.people.PersonDetail
import hu.bme.aut.android.securityapp.data.repository.PersonRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val repository: PersonRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private var _userData = MutableStateFlow(PersonDetail())
    val userData = _userData.asStateFlow()

    init {
        loadData(LoggedPerson.ID)
    }

    fun changeUserData(userData: PersonDetail){
        _userData.value = userData
    }

    private fun loadData(personId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val person = repository.getPerson(LoggedPerson.ID)
            when(person){
                is Resource.Success -> {
                    val data = person.data!!
                    _userData.update {
                        it.copy(
                            fullName = data.fullName,
                            username = data.username,
                            nickname = data.nickname,
                            email = data.email,
                        )
                    }
                }
                is Resource.Error -> {
                    // TODO: error kezelese
                }
            }
        }
    }

    fun updatePerson(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePerson(
                personId = LoggedPerson.ID,
                person = _userData.value
            )
        }
    }
}