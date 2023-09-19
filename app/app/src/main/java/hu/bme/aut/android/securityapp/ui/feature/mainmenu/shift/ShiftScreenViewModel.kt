package hu.bme.aut.android.securityapp.ui.feature.mainmenu.shift

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.shift.CreateShiftData
import hu.bme.aut.android.securityapp.data.model.shift.Shift
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.data.repository.ShiftRepository
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
class ShiftScreenViewModel @Inject constructor(
    private val shiftRepository: ShiftRepository,
    private val wageRepository: WageRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val state = _state.asStateFlow()

    private val _shiftState = MutableStateFlow<ShiftState>(ShiftState())
    val shiftState = _shiftState.asStateFlow()

    private val _wages = MutableStateFlow<List<Wage>>(listOf())
    val wages = _wages.asStateFlow()

    private val _wage = MutableStateFlow<Wage>(Wage())
    val wage = _wage.asStateFlow()

    init {
        loadAllWages()
        loadCurrentShiftState()
    }

    private fun loadAllWages(){
        _state.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = wageRepository.getWages(jobId = LoggedPerson.CURRENT_JOB_ID)

            when(result){
                is Resource.Success -> {
                    _state.value = ScreenState.Success()
                    _wages.value = result.data!!
                    _wage.value = _wages.value.first()
                }
                is Resource.Error -> {
                    _state.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun loadCurrentShiftState(){
        _state.value = ScreenState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            var result = shiftRepository.getCurrentForPerson(LoggedPerson.CURRENT_JOB_ID, LoggedPerson.ID)

            when(result){
                is Resource.Success -> {
                    _state.value = ScreenState.Success()
                    _shiftState.update {
                        it.copy(
                            isActive = true,
                            shift = result.data
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = ScreenState.Error(message = result.message!!)
                    _shiftState.update {
                        it.copy(
                            isActive = false,
                            shift = null
                        )
                    }
                }
            }
        }
        Log.d("SHIFT_STATE", if (_shiftState.value.isActive) "Aktiv" else "Nincs")
    }

    private fun startShift(){
        val shiftData = CreateShiftData(
            jobId = LoggedPerson.CURRENT_JOB_ID,
            personId = LoggedPerson.ID,
            wageId = _wage.value.id,
        )

        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ScreenState.Loading()
            val result = shiftRepository.createShift(shiftData = shiftData)

            when(result){
                is Resource.Success -> {
                    _state.value = ScreenState.Success()
                    _shiftState.update {
                        it.copy(
                            isActive = result.data != null,
                            shift = result.data
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun stopShift(){
        val shiftId = _shiftState.value.shift?.id ?: return

        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ScreenState.Loading()
            val result = shiftRepository.endShift(shiftId = shiftId)

            when(result){
                is Resource.Success -> {
                    _state.value = ScreenState.Success()
                    _shiftState.update {
                        it.copy(
                            isActive = false,
                            shift = null
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    fun evoke(event: ShiftEvent){
        when(event){
            is ShiftEvent.ChangeWage -> {
                _wage.value = event.wage
            }

            ShiftEvent.StartShift -> {
                Log.d("SHIFT_START", _wage.value.toString())
                startShift()
            }

            ShiftEvent.StopShift -> {
                stopShift()
            }

            ShiftEvent.RefreshData -> {
                loadCurrentShiftState()
            }
        }
    }
}

sealed class ShiftEvent{
    class ChangeWage(val wage: Wage) : ShiftEvent()
    object StartShift : ShiftEvent()
    object StopShift : ShiftEvent()
    object RefreshData : ShiftEvent()
}

data class ShiftState(
    val isActive: Boolean = false,
    val shift: Shift? = null
)