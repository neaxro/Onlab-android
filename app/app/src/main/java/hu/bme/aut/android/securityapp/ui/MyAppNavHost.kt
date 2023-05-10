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
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.dashboard.CreateDashboardMessageScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.dashboard.CreateDashboardMessageViewModel
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.dashboard.DashboardDetailScreen
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
    startDestination: String = Screen.Login.baseRoute
) =
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){

        composable(route = Screen.Login.baseRoute){
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(route = Screen.Register.baseRoute){
            val viewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(route = Screen.NoJob.baseRoute){
            NoJobScreen(navController = navController)
        }

        composable(route = Screen.CreateJob.baseRoute){
            val viewModel = hiltViewModel<CreateJobViewModel>()
            CreateJobScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(route = Screen.MainMenu.baseRoute){
            MainMenuScreen(navController = navController)
        }

        composable(route = Screen.ConnectToJob.baseRoute){
            val viewModel = hiltViewModel<ConnectToJobViewModel>()
            ConnectToJobScreen(viewModel = viewModel) {
                navController.navigate(Screen.MainMenu.baseRoute){
                    popUpTo(Screen.ConnectToJob.baseRoute){
                        inclusive = true
                    }
                }
            }
        }

        composable(
            route = Screen.JobDetail.fullRoute,
            arguments = listOf(
                navArgument("jobId"){type = NavType.IntType}
            )
        ){
            val viewModel = hiltViewModel<JobDetailViewModel>()
            JobDetailScreen(
                jobId = it.arguments?.getInt("jobId")!!,
                viewModel = viewModel,
                onNavigateBack = {
                    navController.navigate(Screen.MainMenu.baseRoute){
                        popUpTo(Screen.JobDetail.fullRoute){
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.CreateDashboardMessage.baseRoute){
            val viewModel = hiltViewModel<CreateDashboardMessageViewModel>()
            CreateDashboardMessageScreen(
                viewModel = viewModel,
                navigateBack = {
                    navController.navigate(Screen.MainMenu.baseRoute){
                        popUpTo(Screen.CreateDashboardMessage.baseRoute){
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.DashboardDetails.fullRoute,
            arguments = listOf(
                navArgument("messageId"){type = NavType.IntType}
            ),
        ){
            DashboardDetailScreen(
                messageId = it.arguments?.getInt("messageId")!!,
                navigateBack = {
                    navController.navigate(Screen.MainMenu.baseRoute){
                        popUpTo(Screen.DashboardDetails.fullRoute){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }