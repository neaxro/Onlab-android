package hu.bme.aut.android.securityapp.ui.feature.mainmenu.jobs

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    val repository: JobRepository,
): ViewModel() {

    var jobs: MutableList<DetailedJob> = mutableStateListOf()

    fun loadAllJobs(onError: (String) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAllJobForPerson(LoggedPerson.ID)

            when(result){
                is Resource.Success -> {
                    jobs.removeAll(jobs)
                    jobs.addAll(result.data!!)
                }
                is Resource.Error -> {
                    viewModelScope.launch(Dispatchers.Main) {
                        onError(result.message!!)
                    }
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