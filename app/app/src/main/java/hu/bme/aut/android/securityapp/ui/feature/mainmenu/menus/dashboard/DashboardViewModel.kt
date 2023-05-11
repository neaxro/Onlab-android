package hu.bme.aut.android.securityapp.ui.feature.mainmenu.menus.dashboard

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.dashboard.Dashboard
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.domain.repository.DashboardRepository
import hu.bme.aut.android.securityapp.domain.repository.PersonRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel@Inject constructor(
    private val repository: DashboardRepository,
    private val personRepository: PersonRepository,
): ViewModel() {

    var person = mutableStateOf<Person>(Person(0, "", "", "", "", null))
    var dashboardMessages: MutableList<Dashboard> = mutableStateListOf()

    fun loadAllDashboards(onError: (String) -> Unit){
        Log.d("JOB_PERSON", "[VM] JobId: ${LoggedPerson.CURRENT_JOB_ID} \tPersonId: ${LoggedPerson.ID}")
        viewModelScope.launch(Dispatchers.IO) {

            val result = repository.getAllDashboards(LoggedPerson.CURRENT_JOB_ID, LoggedPerson.ID)

            when(result){
                is Resource.Success -> {
                    dashboardMessages.removeAll(dashboardMessages)
                    dashboardMessages.addAll(result.data!!)
                }
                is Resource.Error -> {
                    viewModelScope.launch(Dispatchers.Main){
                        if(LoggedPerson.CURRENT_JOB_ID > 0){
                            onError(result.message!!)
                        }
                    }
                }
            }
        }
    }

    fun loadPersonData(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = personRepository.getPerson(LoggedPerson.ID)

            when(result){
                is Resource.Success -> {
                    person.value = result.data!!
                }
                is Resource.Error -> {
                    person.value = person.value.copy(fullName = "Not Found")
                }
            }
        }
    }
}