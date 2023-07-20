package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.people.PersonDetail
import hu.bme.aut.android.securityapp.data.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleScreenViewModel @Inject constructor(
    private val repository: JobRepository
): ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val state = _state.asStateFlow()

    private val _people = MutableStateFlow<List<PersonDetail>>(listOf<PersonDetail>())
    val people = _people.asStateFlow()

    init {
        loadPeople()
    }

    fun loadPeople(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ScreenState.Loading()
            val people = repository.getAllPersonOnJob(LoggedPerson.CURRENT_JOB_ID)

            when(people){
                is Resource.Success -> {
                    _state.value = ScreenState.Success()
                    _people.value = people.data!!
                }
                is Resource.Error -> {
                    _state.value = ScreenState.Error(message = people.message!!)
                }
            }
        }
    }
}