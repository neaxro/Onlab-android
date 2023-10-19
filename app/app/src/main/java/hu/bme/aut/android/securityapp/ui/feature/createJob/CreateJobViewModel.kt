package hu.bme.aut.android.securityapp.ui.feature.createJob

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.job.CreateJobData
import hu.bme.aut.android.securityapp.data.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateJobViewModel @Inject constructor(
    private val jobRepository: JobRepository
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _creationState = MutableStateFlow<CreationState>(CreationState.NotCreated)
    val creationState = _creationState.asStateFlow()

    private val _createJobData = MutableStateFlow<CreateJobData>(CreateJobData(ownerId = LoggedPerson.ID))
    val createJobData = _createJobData.asStateFlow()

    fun evoke(action: CreateJobAction){
        when(action){
            CreateJobAction.CreateJob -> {
                createJob()
            }
            is CreateJobAction.UpdateJobName -> {
                _createJobData.update {
                    it.copy(
                        title = action.jobName
                    )
                }
            }
            is CreateJobAction.UpdateDescription -> {
                _createJobData.update {
                    it.copy(
                        description = action.description
                    )
                }
            }
        }
    }

    private fun createJob(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = jobRepository.createJob(_createJobData.value)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success(message = result.message!!, show = true)
                    _creationState.value = CreationState.Created
                }

                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                    _creationState.value = CreationState.Error
                }
            }
        }
    }
}

sealed class CreateJobAction{
    object CreateJob : CreateJobAction()
    class UpdateJobName(val jobName: String) : CreateJobAction()
    class UpdateDescription(val description: String) : CreateJobAction()
}

sealed class CreationState{
    object Created : CreationState()
    object NotCreated : CreationState()
    object Error : CreationState()
}