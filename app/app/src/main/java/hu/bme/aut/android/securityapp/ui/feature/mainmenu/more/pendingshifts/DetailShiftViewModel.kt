package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.pendingshifts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
class DetailShiftViewModel @Inject constructor(
    private val shiftRepository: ShiftRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val state = _state.asStateFlow()

    private val _alertDialogState = MutableStateFlow<ShiftDetailAlertDialogState>(ShiftDetailAlertDialogState.None)
    val alertDialogState = _alertDialogState.asStateFlow()

    private val _shift = MutableStateFlow<Shift>(Shift())
    val shift = _shift.asStateFlow()

    init {
        val shiftId = checkNotNull<Int>(savedStateHandle["shiftId"])
        getShift(shiftId = shiftId)
    }

    private fun getShift(shiftId: Int){
        _state.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = shiftRepository.getById(shiftId = shiftId)

            when(result){
                is Resource.Success -> {
                    _state.value = ScreenState.Success()
                    _shift.value = result.data!!
                }
                is Resource.Error -> {
                    _state.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun acceptShift(){
        _state.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = shiftRepository.acceptShift(shiftId = _shift.value.id)

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

    private fun denyShift(){
        _state.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = shiftRepository.denyShift(shiftId = _shift.value.id)

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

    fun evoke(action: ShiftDetailAction){
        when(action){
            ShiftDetailAction.Accept -> {
                _alertDialogState.value = ShiftDetailAlertDialogState.None
                acceptShift()
            }

            ShiftDetailAction.Deny -> {
                _alertDialogState.value = ShiftDetailAlertDialogState.None
                denyShift()
            }

            ShiftDetailAction.AcceptIntent -> {
                _alertDialogState.value = ShiftDetailAlertDialogState.AcceptIntent
            }

            ShiftDetailAction.DenyIntent -> {
                _alertDialogState.value = ShiftDetailAlertDialogState.DenyIntent
            }

            ShiftDetailAction.Dismiss -> {
                _alertDialogState.value = ShiftDetailAlertDialogState.None
            }

            ShiftDetailAction.Refresh -> {
                getShift(shiftId = _shift.value.id)
            }
        }
    }
}

sealed class ShiftDetailAction{
    object Accept : ShiftDetailAction()
    object Deny : ShiftDetailAction()
    object AcceptIntent : ShiftDetailAction()
    object DenyIntent : ShiftDetailAction()
    object Dismiss : ShiftDetailAction()
    object Refresh : ShiftDetailAction()
}

sealed class ShiftDetailAlertDialogState{
    object AcceptIntent : ShiftDetailAlertDialogState()
    object DenyIntent : ShiftDetailAlertDialogState()
    object None : ShiftDetailAlertDialogState()
}