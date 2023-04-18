package hu.bme.aut.android.securityapp.feature.connecttojob

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.domain.repository.JobRepository
import javax.inject.Inject

@HiltViewModel
class ConnectToJobViewModel @Inject constructor(
    val jobRepository: JobRepository
): ViewModel() {
    
}