package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.people

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.job.ChangeRoleData
import hu.bme.aut.android.securityapp.data.model.job.ChangeWageData
import hu.bme.aut.android.securityapp.data.model.people.PersonDetail
import hu.bme.aut.android.securityapp.data.model.role.Role
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.data.repository.JobRepository
import hu.bme.aut.android.securityapp.data.repository.RoleRepository
import hu.bme.aut.android.securityapp.data.repository.WageRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailScreenViewModel @Inject constructor(
    private val jobRepository: JobRepository,
    private val wageRepository: WageRepository,
    private val roleRepository: RoleRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val state = _state.asStateFlow()

    private val _wages = MutableStateFlow<List<Wage>>(listOf())
    val wages = _wages.asStateFlow()

    private val _roles = MutableStateFlow<List<Role>>(listOf())
    val roles = _roles.asStateFlow()

    private val _person = MutableStateFlow<PersonDetail>(PersonDetail())
    val person = _person.asStateFlow()

    init {
        val personId = checkNotNull<Int>(savedStateHandle["personId"])
        loadPerson(personId = personId)
        loadAllRoles()
        loadAllWages()
    }

    private fun loadAllWages(){
        _state.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = wageRepository.getWages(jobId = LoggedPerson.CURRENT_JOB_ID)

            when(result){
                is Resource.Success -> {
                    _state.value = ScreenState.Success()
                    _wages.value = result.data!!
                }
                is Resource.Error -> {
                    _state.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun loadAllRoles(){
        _state.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = roleRepository.getAllRoles()

            when(result){
                is Resource.Success -> {
                    _state.value = ScreenState.Success()
                    _roles.value = result.data!!
                }
                is Resource.Error -> {
                    _state.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun loadPerson(personId: Int){
        _state.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = jobRepository.getDetailedPersonDataInJob(jobId = LoggedPerson.CURRENT_JOB_ID, personId = personId)

            when(result){
                is Resource.Success -> {
                    _state.value = ScreenState.Success()
                    _person.value = result.data!!
                }
                is Resource.Error -> {
                    _state.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    fun saveChanges(newWage: Wage, newRole: Role){
        _state.value = ScreenState.Loading()

        val changeWage = ChangeWageData(
            personId = _person.value.basicInfo.id,
            wageId = newWage.id
        )
        val changeRole = ChangeRoleData(
            personId = _person.value.basicInfo.id,
            roleId = newRole.id
        )

        saveWageChange(changeWageData = changeWage)
        saveRoleChange(changeRoleData = changeRole)
    }

    private fun saveWageChange(changeWageData: ChangeWageData) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = jobRepository.changeWageForPerson(jobId = LoggedPerson.CURRENT_JOB_ID, changeWageData = changeWageData)

            when(result){
                is Resource.Success -> {
                    _state.value = ScreenState.Success()
                }
                is Resource.Error -> {
                    _state.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun saveRoleChange(changeRoleData: ChangeRoleData) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = jobRepository.changeRoleForPerson(jobId = LoggedPerson.CURRENT_JOB_ID, changeRoleData = changeRoleData)

            when(result){
                is Resource.Success -> {
                    _state.value = ScreenState.Success()
                }
                is Resource.Error -> {
                    _state.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }
}