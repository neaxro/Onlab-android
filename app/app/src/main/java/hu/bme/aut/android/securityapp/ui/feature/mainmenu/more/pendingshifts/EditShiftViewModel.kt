package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.pendingshifts

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EditShiftViewModel @Inject constructor(
    private val shiftRepository: ShiftRepository,
    private val wageRepository: WageRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _shift = MutableStateFlow<Shift>(Shift())
    val shift = _shift.asStateFlow()

    private val _wages = MutableStateFlow<List<Wage>>(listOf())
    val wages = _wages.asStateFlow()

    init {
        val shiftId = checkNotNull<Int>(savedStateHandle["shiftId"])
        getShift(shiftId = shiftId)
        loadAllWages()
    }

    private fun getShift(shiftId: Int){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = shiftRepository.getById(shiftId = shiftId)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _shift.value = result.data!!
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun loadAllWages(){
        viewModelScope.launch(Dispatchers.IO) {
            val wages = wageRepository.getWages(LoggedPerson.CURRENT_JOB_ID)

            when(wages){
                is Resource.Success -> {
                    _wages.value = wages.data!!
                    _screenState.value = ScreenState.Success()
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = wages.message!!)
                }
            }
        }
    }

    fun evoke(action: EditShiftAction){
        when(action){
            is EditShiftAction.SetStartTime -> {
                _shift.update {
                    it.copy(
                        startTime = parseLocalDateTimeToString(action.time)
                    )
                }
            }
            is EditShiftAction.SetStartDate -> {
                _shift.update {
                    it.copy(
                        startTime = parseLocalDateTimeToString(action.time)
                    )
                }
            }
            is EditShiftAction.SetEndTime -> {
                _shift.update {
                    it.copy(
                        endTime = parseLocalDateTimeToString(action.time)
                    )
                }
            }
            is EditShiftAction.SetEndDate -> {
                _shift.update {
                    it.copy(
                        endTime = parseLocalDateTimeToString(action.time)
                    )
                }
            }

            is EditShiftAction.SetWage -> {
                val selectedWage = _wages.value.firstOrNull {
                    it.name == action.wageName
                }

                if(selectedWage != null) {
                    _shift.update {
                        it.copy(
                            wage = selectedWage
                        )
                    }
                }
            }

            EditShiftAction.SaveChanges -> {
                saveChanges()
            }
        }
    }

    private fun saveChanges(){
        Log.d("EditShift_SaveChanges", shift.value.toString())
    }
}

private fun parseLocalDateTimeToString(localDateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    return localDateTime.format(formatter)
}

sealed class EditShiftAction{
    class SetStartTime(val time: LocalDateTime) : EditShiftAction()
    class SetStartDate(val time: LocalDateTime) : EditShiftAction()
    class SetEndTime(val time: LocalDateTime) : EditShiftAction()
    class SetEndDate(val time: LocalDateTime) : EditShiftAction()
    class SetWage(val wageName: String) : EditShiftAction()
    object SaveChanges : EditShiftAction()
}