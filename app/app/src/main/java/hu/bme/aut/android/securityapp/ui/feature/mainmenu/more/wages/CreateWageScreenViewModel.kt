package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.wages

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateWageScreenViewModel @Inject constructor(
    private val wageRepository: WageRepository,
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Finished())
    val screenState = _screenState.asStateFlow()

    private val _wage = MutableStateFlow<WageCreate>(WageCreate(jobId = LoggedPerson.CURRENT_JOB_ID))
    val wage = _wage.asStateFlow()

    private val defaultPrice = 1300.0

    fun evoke(action: CreateWageAction){
        when(action){
            CreateWageAction.CreateWage -> {
                createWage()
            }
            is CreateWageAction.UpdateWageName -> {
                _wage.update {
                    it.copy(
                        name = action.newName
                    )
                }
            }
            is CreateWageAction.UpdateWagePrice -> {
                _wage.update {
                    it.copy(
                        price = action.newPrice.toDoubleOrNull() ?: defaultPrice
                    )
                }
            }
        }
    }

    private fun createWage(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = wageRepository.createWage(wageData = _wage.value)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                }

                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }
}

sealed class CreateWageAction{
    object CreateWage : CreateWageAction()
    class UpdateWageName(val newName: String) : CreateWageAction()
    class UpdateWagePrice(val newPrice: String) : CreateWageAction()
}