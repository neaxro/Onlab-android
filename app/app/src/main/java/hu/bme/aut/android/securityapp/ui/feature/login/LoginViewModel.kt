package hu.bme.aut.android.securityapp.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.people.LoginData
import hu.bme.aut.android.securityapp.data.model.people.LoginResponse
import hu.bme.aut.android.securityapp.data.repository.JobRepository
import hu.bme.aut.android.securityapp.data.repository.LoginRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val jobRepository: JobRepository
): ViewModel() {
    var username = mutableStateOf("nemesa")
    var password = mutableStateOf("Asdasd11")

    var numberOfJobs = mutableStateOf(0)

    fun loginUser(onSuccess: (Int) -> Unit, onError: (String) -> Unit){
        if(username.value.isEmpty() || password.value.isEmpty()) return

        val loginData = LoginData(username.value.trim(), password.value.trim())
        viewModelScope.launch(Dispatchers.IO) {
            val loginResult: Resource<LoginResponse> = repository.loginPerson(loginData)

            when(loginResult){
                is Resource.Success<LoginResponse> -> {
                    LoggedPerson.ID = loginResult.data!!.id
                    LoggedPerson.TOKEN = loginResult.data.token

                    // TODO: Save data into shared preferences

                    // Check for number of jobs the Person is participated
                    checkPersonJobs(LoggedPerson.ID, jobRepository){
                        numberOfJobs.value = it
                        onSuccess(it)
                        //login.value = "Success! ID: ${LoggedPerson.ID} #Jobs: ${numberOfJobs.value}"
                    }
                }
                is Resource.Error<LoginResponse> -> {
                    withContext(Dispatchers.Main){
                        onError(loginResult.message!!)
                    }
                }
            }
        }
    }

    private fun checkPersonJobs(personId: Int, jobRepository: JobRepository, onResponse: (Int)->Unit){
        viewModelScope.launch {
            val jobs = jobRepository.getAllJobForPerson(personId)

            when(jobs){
                is Resource.Success -> {
                    onResponse(jobs.data!!.size)
                }
                else -> {}
            }
        }
    }
}