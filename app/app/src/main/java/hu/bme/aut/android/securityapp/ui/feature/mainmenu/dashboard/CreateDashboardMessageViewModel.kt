package hu.bme.aut.android.securityapp.ui.feature.mainmenu.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.dashboard.asCreateDashboardData
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.data.repository.DashboardRepository
import hu.bme.aut.android.securityapp.data.repository.WageRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.ui.model.DashboardUi
import hu.bme.aut.android.securityapp.ui.model.asDashboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateDashboardMessageViewModel @Inject constructor(
    private val repository: DashboardRepository,
    private val wageRepository: WageRepository,
): ViewModel() {

    private val _screenState = MutableStateFlow<CreateDashboardState>(CreateDashboardState())
    val state = _screenState.asStateFlow()

    private val _wages = MutableStateFlow<List<Wage>>(listOf())
    val wages = _wages.asStateFlow()

    init {
        loadWages()
    }

    fun onEvoke(event: CreateDashboardEvent){
        when(event) {
            is CreateDashboardEvent.ChangeTitle -> {
                _screenState.update {
                    it.copy(
                        message = it.message?.copy(title = event.title)
                    )
                }
            }
            is CreateDashboardEvent.ChangeMessageBody -> {
                _screenState.update {
                    it.copy(
                        message = it.message?.copy(message = event.body)
                    )
                }
            }
            is CreateDashboardEvent.ChangeWage -> {
                _screenState.update {
                    it.copy(
                        message = it.message?.copy(wage = event.wage)
                    )
                }
            }

            CreateDashboardEvent.SaveDashboard -> {
                saveDashboard()
            }
        }

    }

    private fun saveDashboard(){
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.update {
                it.copy(isLoading = true)
            }
            val dashboard = _screenState.value.message!!.asDashboard().asCreateDashboardData()
            repository.createDashboard(dashboard = dashboard)

            _screenState.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun loadWages(){
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.update { it.copy(isLoading = true) }
            val result = wageRepository.getCategories(LoggedPerson.CURRENT_JOB_ID)

            when(result){
                is Resource.Success -> {
                    _screenState.update { it.copy(isLoading = false) }
                    _wages.value = result.data!!

                    onEvoke(CreateDashboardEvent.ChangeWage(_wages.value[0]))
                }
                is Resource.Error -> {
                    _screenState.update { it.copy(error = result.message!!) }
                }
            }
        }
    }
}

data class CreateDashboardState(
    val message: DashboardUi? = DashboardUi(),
    val isLoading: Boolean = false,
    val error: String = "",
)

sealed class CreateDashboardEvent{
    object SaveDashboard: CreateDashboardEvent()
    data class ChangeTitle(val title: String): CreateDashboardEvent()
    data class ChangeMessageBody(val body: String): CreateDashboardEvent()
    data class ChangeWage(val wage: Wage): CreateDashboardEvent()
}