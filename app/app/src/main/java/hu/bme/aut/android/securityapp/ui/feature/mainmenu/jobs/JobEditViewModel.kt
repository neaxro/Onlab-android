package hu.bme.aut.android.securityapp.ui.feature.mainmenu.jobs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.DataFieldErrors
import hu.bme.aut.android.securityapp.constants.validateJobDescription
import hu.bme.aut.android.securityapp.constants.validateJobTitle
import hu.bme.aut.android.securityapp.data.model.job.UpdateJobData
import hu.bme.aut.android.securityapp.data.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class JobEditViewModel @Inject constructor(
    private val jobRepository: JobRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _updateJobData = MutableStateFlow<UpdateJobData>(UpdateJobData())
    val updateJobData = _updateJobData.asStateFlow()

    private val _errors = MutableStateFlow(JobEditErrors())
    val errors = _errors.asStateFlow()

    private var jobId by Delegates.notNull<Int>()

    init {
        jobId = checkNotNull<Int>(savedStateHandle["jobId"])
        loadJob(jobId = jobId)
    }

    fun evoke(action: JobEditAction){
        when(action){
            JobEditAction.UpdateJob -> {
                updateJob()
            }
            is JobEditAction.UpdateDescription -> {
                _updateJobData.update {
                    it.copy(
                        description = action.description
                    )
                }
                checkErrors()
            }
            is JobEditAction.UpdateTitle -> {
                _updateJobData.update {
                    it.copy(
                        title = action.title
                    )
                }
                checkErrors()
            }
        }
    }

    private fun checkErrors(){
        _errors.update {
            it.copy(
                titleError = validateJobTitle(title = _updateJobData.value.title),
                descriptionError = validateJobDescription(description = _updateJobData.value.description)
            )
        }
    }

    private fun loadJob(jobId: Int){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = jobRepository.getJobById(jobId)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()

                    with(result.data!!){
                        _updateJobData.update {
                            it.copy(
                                title = title,
                                description = description
                            )
                        }
                    }

                    checkErrors()
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun updateJob(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = jobRepository.updateJob(jobId = jobId, updateJobData = _updateJobData.value)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success(message = result.message!!, show = true)
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }
}

sealed class JobEditAction{
    object UpdateJob : JobEditAction()
    class UpdateTitle(val title: String) : JobEditAction()
    class UpdateDescription(val description: String) : JobEditAction()
}

data class JobEditErrors(
    val titleError: DataFieldErrors = DataFieldErrors.NoError,
    val descriptionError: DataFieldErrors = DataFieldErrors.NoError,
)

fun JobEditErrors.isError(): Boolean{
    return this.titleError !is DataFieldErrors.NoError
            || this.descriptionError !is DataFieldErrors.NoError
}