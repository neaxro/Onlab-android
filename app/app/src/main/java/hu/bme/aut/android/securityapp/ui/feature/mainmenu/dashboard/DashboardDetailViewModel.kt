package hu.bme.aut.android.securityapp.ui.feature.mainmenu.dashboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.dashboard.asDashboardUi
import hu.bme.aut.android.securityapp.data.model.dashboard.asUpdateDashboardData
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
class DashboardDetailViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val repository: DashboardRepository,
    private val wageRepository: WageRepository,
): ViewModel() {

    private val _screenState = MutableStateFlow<DashboardDetailState>(DashboardDetailState())
    val screenState = _screenState.asStateFlow()

    private val _message = MutableStateFlow<DashboardUi>(DashboardUi())
    val message = _message.asStateFlow()

    private val _wages = MutableStateFlow<List<Wage>>(listOf())
    val wages = _wages.asStateFlow()

    init {
        val messageId = checkNotNull<Int>(savedState["messageId"])
        loadMessage(messageId = messageId)

        loadWages()
    }

    fun onEvoke(event: DashboardDetailEvent){
        when(event){
            DashboardDetailEvent.EditingDashboard -> {
                _screenState.update {
                    it.copy(
                        isEditing = true
                    )
                }
            }

            DashboardDetailEvent.StopEditingDashboard -> {
                _screenState.update {
                    it.copy(
                        isEditing = false
                    )
                }
            }

            is DashboardDetailEvent.ChangeTitle -> {
                _screenState.update {
                    it.copy(
                        message = it.message?.copy(title = event.title)
                    )
                }
            }
            is DashboardDetailEvent.ChangeMessageBody -> {
                _screenState.update {
                    it.copy(
                        message = it.message?.copy(message = event.body)
                    )
                }
            }
            is DashboardDetailEvent.ChangeWage -> {
                _screenState.update {
                    it.copy(
                        message = it.message?.copy(wage = event.wage)
                    )
                }
            }
            is DashboardDetailEvent.ChangeError -> {
                _screenState.update {
                    it.copy(
                        error = event.error
                    )
                }
            }
            DashboardDetailEvent.UpdateDashboard -> {
                updateDashboard()
            }
            DashboardDetailEvent.DeleteDashboard -> {
                deleteDashboard()
            }
        }
    }

    private fun updateDashboard(){
        val dashboard = _screenState.value.message!!.asDashboard()
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDashboard(
                dashboardId = dashboard.id,
                dashboardData = dashboard.asUpdateDashboardData()
            )
        }
    }

    private fun deleteDashboard(){
        val dashboard = _screenState.value.message!!.asDashboard()
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDashboard(
                dashboardId = dashboard.id
            )
        }
    }

    private fun loadMessage(messageId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.update { it.copy(isLoading = true) }
            val result = repository.getDashboard(messageId)

            when(result){
                is Resource.Success -> {
                    _screenState.update {
                        it.copy(
                            isLoading = false,
                            message = result.data!!.asDashboardUi()
                        )
                    }
                }
                is Resource.Error -> {
                    _screenState.update { it.copy(error = result.message!!) }
                }
            }
        }
    }

    private fun loadWages(){
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.update { it.copy(isLoading = true) }
            val result = wageRepository.getCategories(LoggedPerson.CURRENT_JOB_ID)
            _screenState.update { it.copy(isLoading = false) }

            when(result){
                is Resource.Success -> {
                    _wages.value = result.data!!
                }
                is Resource.Error -> {
                    _screenState.update { it.copy(error = result.message!!) }
                }
            }
        }
    }
}

data class DashboardDetailState(
    val message: DashboardUi? = null,
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val error: String = "",
)

sealed class DashboardDetailEvent{
    object EditingDashboard: DashboardDetailEvent()
    object StopEditingDashboard: DashboardDetailEvent()
    data class ChangeTitle(val title: String): DashboardDetailEvent()
    data class ChangeMessageBody(val body: String): DashboardDetailEvent()
    data class ChangeWage(val wage: Wage): DashboardDetailEvent()
    data class ChangeError(val error: String): DashboardDetailEvent()
    object UpdateDashboard: DashboardDetailEvent()
    object DeleteDashboard: DashboardDetailEvent()
}