package hu.bme.aut.android.securityapp.ui.feature.mainmenu.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.dashboard.Dashboard
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.data.repository.DashboardRepository
import hu.bme.aut.android.securityapp.data.repository.PersonRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.Roles
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel@Inject constructor(
    private val dashboardRepository: DashboardRepository,
    private val personRepository: PersonRepository,
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _person = MutableStateFlow<Person>(Person())
    val person = _person.asStateFlow()

    private val _messages = MutableStateFlow<List<Dashboard>>(listOf())
    val messages = _messages.asStateFlow()

    init {
        loadPersonData()

        val personRole = LoggedPerson.getRole()
        if(personRole is Roles.Admin || personRole is Roles.Owner){
            loadAllForAdmin()
        }
        else{
            loadDashboardsForUser()
        }
    }

    private fun loadDashboardsForUser(){
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value = ScreenState.Loading()
            val result = dashboardRepository.getAllDashboardsForPerson(jobId = LoggedPerson.CURRENT_JOB_ID, personId = LoggedPerson.ID)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _messages.value = result.data!!
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun loadAllForAdmin(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = dashboardRepository.getAllDashboardsForJob(jobId = LoggedPerson.CURRENT_JOB_ID)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _messages.value = result.data!!
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun loadPersonData(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = personRepository.getPerson(personId = LoggedPerson.ID)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _person.value = result.data!!
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }
}