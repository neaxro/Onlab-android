package hu.bme.aut.android.securityapp.feature.mainmenu.menus.jobs

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.domain.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    val repository: JobRepository,
): ViewModel() {

    var jobs: MutableList<DetailedJob> = mutableStateListOf()

    fun loadAllJobs(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAllJobForPerson(LoggedPerson.ID)

            when(result){
                is Resource.Success -> {
                    jobs.removeAll(jobs)
                    jobs.addAll(result.data!!)
                }
                is Resource.Error -> {
                    // TODO: Show error!
                }
            }
        }
    }
}