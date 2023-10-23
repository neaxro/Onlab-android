package hu.bme.aut.android.securityapp.ui.feature.mainmenu.jobs

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.AppRemember
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    private val jobRepository: JobRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _jobs = MutableStateFlow<List<DetailedJob>>(listOf())
    val jobs = _jobs.asStateFlow()

    init {
        loadAllJobs()
    }

    private fun loadAllJobs(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = jobRepository.getAllJobForPerson(LoggedPerson.ID)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _jobs.value = result.data!!
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    fun evoke(action: JobsAction){
        when(action){
            is JobsAction.SelectJob -> {
                selectJob(jobId = action.jobId)
            }
        }
    }

    private fun selectJob(jobId: Int) {
        if(LoggedPerson.CURRENT_JOB_ID == jobId) return

        LoggedPerson.CURRENT_JOB_ID = jobId
        AppRemember.rememberSelectedJobId(sharedPreferences = sharedPreferences, jobId = jobId)

        viewModelScope.launch(Dispatchers.IO) {
            val result = jobRepository.selectJob(jobId = LoggedPerson.CURRENT_JOB_ID, personId = LoggedPerson.ID)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success(message = result.message!!, show = true)
                    LoggedPerson.TOKEN = result.data!!.token
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }
}

sealed class JobsAction{
    class SelectJob(val jobId: Int) : JobsAction()
}