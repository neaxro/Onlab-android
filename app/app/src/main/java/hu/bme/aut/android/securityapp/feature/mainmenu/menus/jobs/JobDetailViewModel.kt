package hu.bme.aut.android.securityapp.feature.mainmenu.menus.jobs

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.domain.repository.JobRepository
import javax.inject.Inject

@HiltViewModel
class JobDetailViewModel @Inject constructor(
    jobRepository: JobRepository
): ViewModel() {

    var job = mutableStateOf(DetailedJob(0, "", "", "", Person(0, "", "", "", "", null)))

    fun getJobById(jobId: Int){
        // TODO
    }
}