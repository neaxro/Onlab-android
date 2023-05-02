package hu.bme.aut.android.securityapp.feature.mainmenu.menus.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.domain.repository.DashboardRepository
import javax.inject.Inject

@HiltViewModel
class CreateDashboardMessageViewModel @Inject constructor(
    repository: DashboardRepository
): ViewModel() {

    var title = mutableStateOf("")
    var message = mutableStateOf("")

    fun createMessage(onSuccess: () -> Unit, onError: (String) -> Unit){

    }
}