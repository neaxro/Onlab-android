package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.wages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.data.repository.WageRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WagesViewModel @Inject constructor(
    private val wageRepository: WageRepository
): ViewModel() {

    private var _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    var screenState = _screenState.asStateFlow()

    private var _wages = MutableStateFlow<List<Wage>>(listOf())
    val wages = _wages.asStateFlow()

    init {
        loadAllWages()
    }

    fun evoke(action: WagesAction){
        when(action){
            WagesAction.Refresh -> {
                loadAllWages()
            }
        }
    }

    private fun loadAllWages(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = wageRepository.getWages(LoggedPerson.CURRENT_JOB_ID)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _wages.value = result.data!!
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }
}

sealed class WagesAction{
    object Refresh : WagesAction()
}