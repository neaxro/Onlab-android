package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.jobstatistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.shift.JobStatistic
import hu.bme.aut.android.securityapp.data.repository.PersonRepository
import hu.bme.aut.android.securityapp.data.repository.ShiftRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobStatisticsViewModel @Inject constructor(
    private val shiftRepository: ShiftRepository,
    private val personRepository: PersonRepository,
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _statistics = MutableStateFlow<JobStatistic>(JobStatistic())
    val statistic = _statistics.asStateFlow()

    init {
        loadStatistics()
    }

    fun evoke(action: JobStatisticsAction){
        when(action){
            JobStatisticsAction.Refresh -> {
                loadStatistics()
            }
        }
    }

    private fun loadStatistics(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = shiftRepository.getJobStatistics(jobId = LoggedPerson.CURRENT_JOB_ID)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _statistics.value = result.data!!
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }
}

sealed class JobStatisticsAction{
    object Refresh : JobStatisticsAction()
}