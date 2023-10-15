package hu.bme.aut.android.securityapp.ui.feature.mainmenu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.DashboardScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.JobsScreen
import hu.bme.aut.android.securityapp.ui.feature.createJob.CreateJobScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.menus.MoreScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.menus.ShiftScreen
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
        startDestination = startDestination,
    ){

        composable(
            route = Screen.Jobs.fullRoute
        ){
            JobsScreen(
                navigateToCreateJob = {
                    navController.navigate(Screen.CreateJob.fullRoute)
                },
                navigateToConnectJob = {
                    navController.navigate(Screen.ConnectToJob.fullRoute)
                },
                navigateToDetailJob = { jobId ->
                    navController.navigate(Screen.JobDetail.withArgs(jobId.toString()))
                }
            )
        }

        composable(
            route = Screen.Dashboard.fullRoute
        ){
            DashboardScreen(
                navigateToCreateMessage = {
                    navController.navigate(Screen.CreateDashboardMessage.fullRoute)
                },
                navigateToDetails = { messageId ->
                    navController.navigate(Screen.DashboardDetails.withArgs(messageId.toString()))
                }
            )
        }

        composable(
            route = Screen.Shift.fullRoute
        ){
            ShiftScreen()
        }

        composable(
            route = Screen.Statistics.fullRoute
        ){
            MoreScreen(
                navigateTo = { url ->
                    if(url.isNotEmpty()){
                        navController.navigate(url)
                    }
                }
            )
        }

        composable(
            route = Screen.CreateJob.baseRoute
        ){
            CreateJobScreen(
                navigateToMainMenu = {
                    mainMenuNavController.navigate(Screen.MainMenu.fullRoute){
                        popUpTo(Screen.MainMenu.fullRoute){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}