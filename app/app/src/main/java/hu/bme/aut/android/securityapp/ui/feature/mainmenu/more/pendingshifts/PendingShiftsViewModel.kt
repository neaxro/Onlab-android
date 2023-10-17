package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.pendingshifts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.shift.Shift
import hu.bme.aut.android.securityapp.data.repository.ShiftRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PendingShiftsViewModel @Inject constructor(
    private val shiftRepository: ShiftRepository
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val state = _screenState.asStateFlow()

    private val _shifts = MutableStateFlow<List<Shift>>(listOf())
    val shifts = _shifts.asStateFlow()

    init {
        loadPendingShifts()
    }

    fun evoke(action: PendingShiftsAction){
        when(action){
            PendingShiftsAction.Refresh -> {
                loadPendingShifts()
            }
        }
    }

    private fun loadPendingShifts(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = shiftRepository.getAllPendingInJob(jobId = LoggedPerson.CURRENT_JOB_ID)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _shifts.value = result.data!!
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }
}

sealed class PendingShiftsAction{
    object Refresh : PendingShiftsAction()
}