package hu.bme.aut.android.securityapp.ui.feature.connecttojob

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectToJobViewModel @Inject constructor(
    val jobRepository: JobRepository
): ViewModel() {

    val numberOfDigits = 6
    var digits = mutableStateOf("")

    fun connectPersonToJob(onSuccess: () -> Unit, onError: (String) -> Unit){

        viewModelScope.launch(Dispatchers.IO) {
            val connection = jobRepository.connectPersonToJob(LoggedPerson.ID, digits.value)

            when(connection){
                is Resource.Success -> {
                    viewModelScope.launch(Dispatchers.Main){
                        onSuccess()
                    }
                }
                is Resource.Error -> {
                    viewModelScope.launch(Dispatchers.Main){
                        onError(connection.message!!)
                    }
                }
            }
        }
    }
}