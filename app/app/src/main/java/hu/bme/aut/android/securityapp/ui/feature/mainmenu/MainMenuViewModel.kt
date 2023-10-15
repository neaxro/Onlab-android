package hu.bme.aut.android.securityapp.ui.feature.mainmenu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Work
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.repository.DashboardRepository
import hu.bme.aut.android.securityapp.data.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import hu.bme.aut.android.securityapp.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository,
    private val jobRepository: JobRepository,
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val screenState = _screenState.asStateFlow()

    private val _bottomNavigationItems = MutableStateFlow<List<NavigationItem>>(
        listOf(
            NavigationItem(id = 0, name = "Jobs", screen = Screen.Jobs, icon = Icons.Rounded.Work, badgeCount = 0),
            NavigationItem(id = 1, name = "Dashboard", screen = Screen.Dashboard, icon = Icons.Rounded.Dashboard, badgeCount = 0),
            NavigationItem(id = 2, name = "Shift", screen = Screen.Shift, icon = Icons.Rounded.Shield, badgeCount = 0),
            NavigationItem(id = 3, name = "More", screen = Screen.Statistics, icon = Icons.Rounded.MoreHoriz, badgeCount = 0),
        )
    )
    val bottomNavigationItems = _bottomNavigationItems.asStateFlow()

    init {
        getJobsCount()
    }

    fun evoke(action: MainMenuAction){
        when(action){
            is MainMenuAction.GetMessagesCount -> {
                getMessagesCount(jobId = action.jobId)
            }
        }
    }

    private fun getMessagesCount(jobId: Int){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = dashboardRepository.getAllDashboardsForJob(jobId = jobId)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _bottomNavigationItems.update { currentList ->
                        val dashboardItem = currentList.find{ it.id == 1 }!!.copy(badgeCount = result.data!!.size)

                        val updatedList = currentList.map { navItem ->
                            if(navItem.id == dashboardItem.id){
                                dashboardItem
                            }
                            else{
                                navItem
                            }
                        }

                        updatedList
                    }
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }

    private fun getJobsCount(){
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = jobRepository.getAllJobForPerson(personId = LoggedPerson.ID)

            when(result){
                is Resource.Success -> {
                    _screenState.value = ScreenState.Success()
                    _bottomNavigationItems.update { currentList ->
                        val jobItem = currentList.find{ it.id == 0 }!!.copy(badgeCount = result.data!!.size)

                        val updatedList = currentList.map { navItem ->
                            if(navItem.id == jobItem.id){
                                jobItem
                            }
                            else{
                                navItem
                            }
                        }

                        updatedList
                    }
                }
                is Resource.Error -> {
                    _screenState.value = ScreenState.Error(message = result.message!!)
                }
            }
        }
    }
}

sealed class MainMenuAction{
    class GetMessagesCount(val jobId: Int) : MainMenuAction()
}