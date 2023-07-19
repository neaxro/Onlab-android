package hu.bme.aut.android.securityapp.ui.feature.wages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.wage.WageCreate
import hu.bme.aut.android.securityapp.data.repository.WageRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateWageScreenViewModel @Inject constructor(
    private val repository: WageRepository,
): ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Finished())
    val state = _state.asStateFlow()

    fun createWage(name: String, price: Double){
        val wage = WageCreate(
            jobId = LoggedPerson.CURRENT_JOB_ID,
            name = name,
            price = price
        )

        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ScreenState.Loading()
            val result = repository.createWage(wageData = wage)

            when(result){
                is Resource.Success -> {
                    _state.value = ScreenState.Success()
                }

                is Resource.Error -> {
                    _state.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }
}