package hu.bme.aut.android.securityapp.ui.feature.mainmenu.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
    val repository: JobRepository,
): ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val state = _state.asStateFlow()

    private val _jobs = MutableStateFlow<List<DetailedJob>>(listOf())
    val jobs = _jobs.asStateFlow()

    //var jobs: MutableList<DetailedJob> = mutableStateListOf()

    init {
        loadAllJobs()
    }

    private fun loadAllJobs(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ScreenState.Loading()
            val result = repository.getAllJobForPerson(LoggedPerson.ID)

            when(result){
                is Resource.Success -> {
                    _state.value = ScreenState.Finished()
                    _jobs.value = result.data!!
                }
                is Resource.Error -> {
                    _state.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    fun selectJob(jobId: Int, onSuccess: (String) -> Unit, onError: (String) -> Unit){
        LoggedPerson.CURRENT_JOB_ID = jobId

        viewModelScope.launch(Dispatchers.IO) {
            val token = repository.selectJob(LoggedPerson.CURRENT_JOB_ID, LoggedPerson.ID)

            when(token){
                is Resource.Success -> {
                    LoggedPerson.TOKEN = token.data!!.token

                    viewModelScope.launch(Dispatchers.Main) {
                        onSuccess("Successfully selected!")
                    }
                }
                is Resource.Error -> {
                    viewModelScope.launch(Dispatchers.Main) {
                        onError(token.message!!)
                    }
                }
            }
        }
    }
}