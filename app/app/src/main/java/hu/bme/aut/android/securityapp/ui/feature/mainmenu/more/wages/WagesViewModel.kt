package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.wages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.data.repository.WageRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WagesViewModel @Inject constructor(
    val repository: WageRepository
): ViewModel() {

    private var _screenState = MutableStateFlow<WagesScreenStates>(WagesScreenStates.Loading)
    var screenState = _screenState.asStateFlow()

    private var _wages = MutableStateFlow<List<Wage>>(listOf())
    val wages = _wages.asStateFlow()

    init {
        loadAllWages()
    }

    fun loadAllWages(){
        viewModelScope.launch(Dispatchers.IO) {
            val wages = repository.getWages(LoggedPerson.CURRENT_JOB_ID)

            when(wages){
                is Resource.Success -> {
                    _wages.value = wages.data!!
                    _screenState.update {
                        WagesScreenStates.Success
                    }
                }
                is Resource.Error -> {
                    _screenState.update {
                        WagesScreenStates.Error(message = wages.message!!)
                    }
                }
            }
        }
    }
}

sealed class WagesScreenStates{
    object Loading : WagesScreenStates()
    object Success : WagesScreenStates()
    class Error(val message: String) : WagesScreenStates()
}