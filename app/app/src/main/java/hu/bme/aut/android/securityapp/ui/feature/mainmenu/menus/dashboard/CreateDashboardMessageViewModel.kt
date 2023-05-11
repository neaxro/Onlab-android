package hu.bme.aut.android.securityapp.ui.feature.mainmenu.menus.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.dashboard.CreateDashboardData
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.domain.repository.DashboardRepository
import hu.bme.aut.android.securityapp.domain.repository.WageRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateDashboardMessageViewModel @Inject constructor(
    private val repository: DashboardRepository,
    private val wageRepository: WageRepository,
): ViewModel() {
    var title = mutableStateOf("")
    var message = mutableStateOf("")
    var selectedCategory = mutableStateOf<Wage?>(null)

    //var categories = mutableListOf<Wage>()
    private val _categories = MutableStateFlow<List<Wage>>(listOf())
    val categories = _categories.asStateFlow()

    init {
        loadCategories({})
    }

    fun loadCategories(onError: (String) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = wageRepository.getCategories(LoggedPerson.CURRENT_JOB_ID)

            when(result){
                is Resource.Success -> {
                    _categories.value = result.data!!
                }
                is Resource.Error -> {
                    viewModelScope.launch(Dispatchers.Main) {
                        onError(result.message!!)
                    }
                }
            }
        }
    }

    fun createMessage(
        message: CreateDashboardData,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.createDashboard(message)

            when(result){
                is Resource.Success -> {
                    viewModelScope.launch(Dispatchers.Main) {
                        onSuccess()
                    }
                }
                is Resource.Error -> {
                    onError(result.message!!)
                }
            }
        }
    }
}