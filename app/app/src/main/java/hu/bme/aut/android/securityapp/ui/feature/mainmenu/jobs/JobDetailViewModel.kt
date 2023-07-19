package hu.bme.aut.android.securityapp.ui.feature.mainmenu.jobs

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.data.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobDetailViewModel @Inject constructor(
    private val jobRepository: JobRepository
): ViewModel() {

    var job = mutableStateOf(DetailedJob(0, "", "", "", Person(0, "", "", "", "", null)))

    fun getJobById(jobId: Int, onError: (String) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = jobRepository.getJobById(jobId)

            when(result){
                is Resource.Success -> {
                    job.value = result.data!!
                }
                is Resource.Error -> {
                    viewModelScope.launch(Dispatchers.Main) {
                        onError(result.message!!)
                    }
                }
            }
        }
    }
}