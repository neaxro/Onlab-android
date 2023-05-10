package hu.bme.aut.android.securityapp.feature.mainmenu.menus.dashboard

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.domain.repository.DashboardRepository
import javax.inject.Inject

@HiltViewModel
class DashboardDetailViewModel @Inject constructor(
    private val repository: DashboardRepository
): ViewModel() {

}