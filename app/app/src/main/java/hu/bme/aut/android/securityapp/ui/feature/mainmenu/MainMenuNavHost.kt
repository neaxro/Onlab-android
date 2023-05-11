package hu.bme.aut.android.securityapp.ui.feature.mainmenu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.DashboardScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.JobsScreen
import hu.bme.aut.android.securityapp.ui.feature.createJob.CreateJobScreen
import hu.bme.aut.android.securityapp.ui.feature.createJob.CreateJobViewModel
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.menus.ShiftScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.menus.StatisticsScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.menus.dashboard.DashboardViewModel
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.menus.jobs.JobsViewModel
import hu.bme.aut.android.securityapp.ui.navigation.Screen
import hu.bme.aut.android.securityapp.ui.navigation.withArgs

@Composable
fun MainMenuNavHost(
    modifier: Modifier = Modifier,
    navController: NavController,
    mainMenuNavController: NavHostController,
    startDestination: String = Screen.Dashboard.baseRoute
){
    NavHost(
        modifier = modifier
            .fillMaxSize(),
        navController = mainMenuNavController,
        startDestination = startDestination
    ){

        composable(route = Screen.Jobs.baseRoute){
            val jobViewModel = hiltViewModel<JobsViewModel>()
            JobsScreen(
                viewModel = jobViewModel,
                navigateToCreateJob = {
                    navController.navigate(Screen.CreateJob.baseRoute)
                },
                navigateToConnectJob = {
                    navController.navigate(Screen.ConnectToJob.baseRoute)
                },
                navigateToDetailJob = { jobId ->
                    navController.navigate(Screen.JobDetail.withArgs(jobId.toString()))
                }
            )
        }

        composable(route = Screen.Dashboard.baseRoute){
            val dashboardViewModel = hiltViewModel<DashboardViewModel>()
            DashboardScreen(
                viewModel = dashboardViewModel,
                navigateToCreateMessage = {
                    navController.navigate(Screen.CreateDashboardMessage.baseRoute)
                },
                navigateToDetails = { messageId ->
                    navController.navigate(Screen.DashboardDetails.withArgs(messageId.toString()))
                }
            )
        }

        composable(route = Screen.Shift.baseRoute){
            ShiftScreen()
        }

        composable(route = Screen.Statistics.baseRoute){
            StatisticsScreen()
        }

        composable(route = Screen.CreateJob.baseRoute){
            val createJobViewModel = hiltViewModel<CreateJobViewModel>()
            CreateJobScreen(viewModel = createJobViewModel, navController = mainMenuNavController)
        }
    }
}