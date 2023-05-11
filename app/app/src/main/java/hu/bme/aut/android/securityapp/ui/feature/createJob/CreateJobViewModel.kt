package hu.bme.aut.android.securityapp.ui.feature.createJob

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.job.CreateJobData
import hu.bme.aut.android.securityapp.domain.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateJobViewModel @Inject constructor(
    private val repository: JobRepository
): ViewModel() {

    var jobname = mutableStateOf("")
    var description = mutableStateOf("")

    fun createJob(onSuccess: () -> Unit, onError: (String) -> Unit){
        val createJobData = CreateJobData(jobname.value, description.value, LoggedPerson.ID)

        viewModelScope.launch(Dispatchers.IO) {
            val createResult = repository.createJob(createJobData)

            when(createResult){
                is Resource.Success -> {
                    withContext(Dispatchers.Main){
                        onSuccess()
                    }
                }

                is Resource.Error -> {
                    withContext(Dispatchers.Main){
                        onError(createResult.message!!)
                    }
                }
            }
        }
    }
}