package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.wages

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.data.model.wage.asUpdate
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
class WageDetailScreenViewModel @Inject constructor(
    private val wageRepository: WageRepository,
    private val stateHandle: SavedStateHandle,
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _wage = MutableStateFlow<Wage>(Wage())
    val wage = _wage.asStateFlow()

    init {
        val wageId = checkNotNull<Int>(stateHandle["wageId"])
        getWage(wageId = wageId)
    }

    fun evoke(action: WageDetailAction){
        when(action){
            is WageDetailAction.UpdateName -> {
                _wage.update {
                    it.copy(name = action.name)
                }
            }
            is WageDetailAction.UpdatePrice -> {
                _wage.update {
                    it.copy(price = action.price)
                }
            }
            WageDetailAction.DeleteWage -> {
                deleteWage()
            }
            WageDetailAction.SaveWage -> {
                saveWage()
            }
        }
    }

    private fun saveWage(){
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value = ScreenState.Loading()
            val result = wageRepository.updateWage(wageId = _wage.value.id, wageData = _wage.value.asUpdate())

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(result.message!!)
                }
            }
        }
    }

    private fun deleteWage(){
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value = ScreenState.Loading()
            val result = wageRepository.deleteWage(wageId = _wage.value.id)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Finished()
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(result.message!!)
                }
            }
        }
    }

    private fun getWage(wageId: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            _screenState.value = ScreenState.Loading()
            val wage = wageRepository.getWage(wageId)

            when(wage){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Finished()
                    _wage.value = wage.data!!
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = wage.message!!)
                }
            }
        }
    }
}

sealed class WageDetailAction{
    class UpdateName(val name: String) : WageDetailAction()
    class UpdatePrice(val price: Double) : WageDetailAction()
    object DeleteWage : WageDetailAction()
    object SaveWage : WageDetailAction()
}