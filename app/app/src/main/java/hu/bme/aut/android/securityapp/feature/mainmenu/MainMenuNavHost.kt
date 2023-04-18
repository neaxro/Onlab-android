package hu.bme.aut.android.securityapp.feature.mainmenu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hu.bme.aut.android.securityapp.feature.createJob.CreateJobScreen
import hu.bme.aut.android.securityapp.feature.createJob.CreateJobViewModel
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.DashboardScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.JobsScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.ShiftScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.StatisticsScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.jobs.JobsViewModel
import hu.bme.aut.android.securityapp.ui.navigation.Screen

@Composable
fun MainMenuNavHost(
    modifier: Modifier = Modifier,
    navController: NavController,
    mainMenuNavController: NavHostController,
    startDestination: String = Screen.Jobs.route
){
    NavHost(
        modifier = modifier
            .fillMaxSize(),
        navController = mainMenuNavController,
        startDestination = startDestination
    ){

        composable(route = Screen.Jobs.route){
            val jobViewModel = hiltViewModel<JobsViewModel>()
            JobsScreen(
                viewModel = jobViewModel,
                navigateToCreateJob = {
                    navController.navigate(Screen.CreateJob.route)
                },
                navigateToConnectJob = {
                    // TODO: Navigate to connect job
                }
            )
        }

        composable(route = Screen.Dashboard.route){
            DashboardScreen()
        }

        composable(route = Screen.Shift.route){
            ShiftScreen()
        }

        composable(route = Screen.Statistics.route){
            StatisticsScreen()
        }

        composable(route = Screen.CreateJob.route){
            val createJobViewModel = hiltViewModel<CreateJobViewModel>()
            CreateJobScreen(viewModel = createJobViewModel, navController = mainMenuNavController)
        }

    }
}