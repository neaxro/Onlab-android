package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.Constants
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
class StatisticsScreenViewModel @Inject constructor(
    private val shiftRepository: ShiftRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private var _allShift = listOf<Shift>()

    private val _shiftsToShow = MutableStateFlow<List<Shift>>(listOf())
    val shifts = _shiftsToShow.asStateFlow()

    init {
        loadJudgedShifts()
    }

    private fun loadJudgedShifts(){
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value = ScreenState.Loading()
            val shifts = shiftRepository.getAllJudgedShifts(jobId = LoggedPerson.CURRENT_JOB_ID, personId = LoggedPerson.ID)

            when (shifts){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _allShift = shifts.data!!
                    changeFilter(StatisticsFilter.All)
                }

                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = shifts.message!!)
                }
            }
        }
    }

    fun changeFilter(filter: StatisticsFilter){
        when(filter){
            StatisticsFilter.All -> _shiftsToShow.value = _allShift.filter { it.status.id != Constants.SHIFT_IN_PROGRESS_STATUS_ID }
            StatisticsFilter.Accepted -> _shiftsToShow.value = _allShift.filter { it.status.id == Constants.SHIFT_ACCEPTED_STATUS_ID }
            StatisticsFilter.Denied -> _shiftsToShow.value = _allShift.filter { it.status.id == Constants.SHIFT_DENIED_STATUS_ID }
            StatisticsFilter.Pending -> _shiftsToShow.value = _allShift.filter { it.status.id == Constants.SHIFT_PENDING_STATUS_ID }
        }
    }
}

sealed class StatisticsFilter(){
    object All : StatisticsFilter()
    object Accepted : StatisticsFilter()
    object Denied : StatisticsFilter()
    object Pending : StatisticsFilter()
}