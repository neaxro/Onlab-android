package hu.bme.aut.android.securityapp.ui.feature.connecttojob

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.DataFieldErrors
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.constants.validateConnectionPin
import hu.bme.aut.android.securityapp.data.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectToJobViewModel @Inject constructor(
    private val jobRepository: JobRepository
): ViewModel() {

    val numberOfDigits = 6

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _digits = MutableStateFlow<String>("")
    val digits = _digits.asStateFlow()

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.NotConnected)
    val connectionState = _connectionState.asStateFlow()

    private val _errors = MutableStateFlow<DataFieldErrors>(DataFieldErrors.NoError)
    val errors = _errors.asStateFlow()

    fun evoke(action: ConnectToJobAction){
        when(action){
            is ConnectToJobAction.ConnectToJob -> {
                if(_errors.value is DataFieldErrors.NoError) {
                    connectPersonToJob()
                }
                else{
                    ScreenState.Error(message = _errors.value.message)
                }
            }

            is ConnectToJobAction.UpdateDigits -> {
                if(action.digits.length <= numberOfDigits){
                    _digits.value = action.digits
                    _errors.value = validateConnectionPin(pin = _digits.value, numberOfDigits = numberOfDigits)
                }
            }
        }
    }

    private fun connectPersonToJob(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = jobRepository.connectPersonToJob(personId = LoggedPerson.ID, pin = _digits.value)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _connectionState.value = ConnectionState.Connected
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                    _connectionState.value = ConnectionState.Error
                }
            }
        }
    }
}

sealed class ConnectionState(val message: String){
    object Connected : ConnectionState(message = "Successfully connected!")
    object Error : ConnectionState(message = "Error occurred...")
    object NotConnected : ConnectionState(message = "")
}

sealed class ConnectToJobAction{
    object ConnectToJob : ConnectToJobAction()
    class UpdateDigits(val digits: String) : ConnectToJobAction()
}