package hu.bme.aut.android.securityapp.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.android.securityapp.feature.connecttojob.ConnectToJobScreen
import hu.bme.aut.android.securityapp.feature.connecttojob.ConnectToJobViewModel
import hu.bme.aut.android.securityapp.feature.createJob.CreateJobScreen
import hu.bme.aut.android.securityapp.feature.createJob.CreateJobViewModel
import hu.bme.aut.android.securityapp.feature.mainmenu.MainMenuScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.dashboard.CreateDashboardMessageViewModel
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.dashboard.CreateDashboardMessageScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.jobs.JobDetailScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.jobs.JobDetailViewModel
import hu.bme.aut.android.securityapp.feature.nojob.NoJobScreen
import hu.bme.aut.android.securityapp.feature.register.RegisterViewModel
import hu.bme.aut.android.securityapp.ui.navigation.Screen
import hu.bme.aut.android.securityapp.ui.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
) =
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){

        composable(route = Screen.Login.route){
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(route = Screen.Register.route){
            val viewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(route = Screen.NoJob.route){
            NoJobScreen(navController = navController)
        }

        composable(route = Screen.CreateJob.route){
            val viewModel = hiltViewModel<CreateJobViewModel>()
            CreateJobScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(route = Screen.MainMenu.route){
            MainMenuScreen(navController = navController)
        }

        composable(route = Screen.ConnectToJob.route){
            val viewModel = hiltViewModel<ConnectToJobViewModel>()
            ConnectToJobScreen(viewModel = viewModel) {
                navController.navigate(Screen.MainMenu.route){
                    popUpTo(Screen.ConnectToJob.route){
                        inclusive = true
                    }
                }
            }
        }

        composable(
            route = Screen.JobDetail.route,
            arguments = listOf(
                navArgument("jobId"){type = NavType.IntType}
            )
        ){
            val viewModel = hiltViewModel<JobDetailViewModel>()
            JobDetailScreen(
                jobId = it.arguments?.getInt("jobId")!!,
                viewModel = viewModel,
                onNavigateBack = {
                    navController.navigate(Screen.MainMenu.route){
                        popUpTo(Screen.JobDetail.route){
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.CreateDashboardMessage.route){
            val viewModel = hiltViewModel<CreateDashboardMessageViewModel>()
            CreateDashboardMessageScreen(
                viewModel = viewModel,
                navigateBack = {
                    navController.navigate(Screen.MainMenu.route){
                        popUpTo(Screen.CreateDashboardMessage.route){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }