package hu.bme.aut.android.securityapp.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.domain.repository.LoginRepository
import javax.inject.Inject
import hu.bme.aut.android.securityapp.data.model.people.LoginData
import hu.bme.aut.android.securityapp.data.model.people.LoginResponse
import hu.bme.aut.android.securityapp.domain.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val jobRepository: JobRepository
): ViewModel() {
    var username = mutableStateOf("")
    var password = mutableStateOf("")

    var login = mutableStateOf("")
    var numberOfJobs = mutableStateOf(0)

    fun LoginUser(loginSuccess: (Int) -> Unit){
        if(username.value.isEmpty() || password.value.isEmpty()) return

        val loginData = LoginData(username.value.trim(), password.value.trim())
        viewModelScope.launch(Dispatchers.IO) {
            val loginResult: Resource<LoginResponse> = repository.loginPerson(loginData)

            when(loginResult){
                is Resource.Success<LoginResponse> -> {
                    LoggedPerson.ID = loginResult.data!!.id
                    LoggedPerson.TOKEN = loginResult.data!!.token

                    // Check for number of jobs the Person is participated
                    checkPersonJobs(LoggedPerson.ID, jobRepository, this@LoginViewModel){
                        numberOfJobs.value = it
                        loginSuccess(it)
                        //login.value = "Success! ID: ${LoggedPerson.ID} #Jobs: ${numberOfJobs.value}"
                    }
                }
                is Resource.Error<LoginResponse> -> {
                    login.value = loginResult.message!!
                }
            }
        }
    }
}

private fun checkPersonJobs(personId: Int, jobRepository: JobRepository, viewModel: ViewModel, onResponse: (Int)->Unit){
    viewModel.viewModelScope.launch {
        val jobs = jobRepository.getAllJobForPerson(personId)

        when(jobs){
            is Resource.Success -> {
                onResponse(jobs.data!!.size)
            }
            else -> {}
        }
    }
}