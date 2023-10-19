package hu.bme.aut.android.securityapp.ui.feature.mainmenu.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.dashboard.CreateDashboardData
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.data.repository.DashboardRepository
import hu.bme.aut.android.securityapp.data.repository.WageRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateDashboardMessageViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository,
    private val wageRepository: WageRepository,
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _message = MutableStateFlow<CreateDashboardData>(CreateDashboardData(jobId = LoggedPerson.CURRENT_JOB_ID, creatorId = LoggedPerson.ID))
    val message = _message.asStateFlow()

    private val _selectedWage = MutableStateFlow<Wage>(Wage())
    val selectedWage = _selectedWage.asStateFlow()

    private val _wages = MutableStateFlow<List<Wage>>(listOf())
    val wages = _wages.asStateFlow()

    init {
        loadWages()
    }

    fun evoke(action: CreateDashboardAction){
        when(action) {
            is CreateDashboardAction.ChangeTitle -> {
                _message.update {
                    it.copy(
                        title = action.title
                    )
                }
            }
            is CreateDashboardAction.ChangeMessageBody -> {
                _message.update {
                    it.copy(
                        message = action.message
                    )
                }
            }
            is CreateDashboardAction.ChangeWage -> {
                _selectedWage.value = action.wage
                _message.update {
                    it.copy(
                        groupId = action.wage.id
                    )
                }
            }
            CreateDashboardAction.SaveDashboard -> {
                createDashboard()
            }
        }

    }

    private fun createDashboard(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = dashboardRepository.createDashboard(dashboard = _message.value)

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

    private fun loadWages(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = wageRepository.getCategories(jobId = LoggedPerson.CURRENT_JOB_ID)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _wages.value = result.data!!

                    evoke(CreateDashboardAction.ChangeWage(_wages.value[0]))
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }
}

sealed class CreateDashboardAction{
    object SaveDashboard: CreateDashboardAction()
    data class ChangeTitle(val title: String): CreateDashboardAction()
    data class ChangeMessageBody(val message: String): CreateDashboardAction()
    data class ChangeWage(val wage: Wage): CreateDashboardAction()
}