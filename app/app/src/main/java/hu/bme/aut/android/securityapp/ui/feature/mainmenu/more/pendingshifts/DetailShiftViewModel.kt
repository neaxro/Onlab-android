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
}