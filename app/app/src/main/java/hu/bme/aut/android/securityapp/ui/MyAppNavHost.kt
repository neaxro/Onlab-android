package hu.bme.aut.android.securityapp.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.securityapp.feature.connecttojob.ConnectToJobScreen
import hu.bme.aut.android.securityapp.feature.connecttojob.ConnectToJobViewModel
import hu.bme.aut.android.securityapp.feature.createJob.CreateJobScreen
import hu.bme.aut.android.securityapp.feature.createJob.CreateJobViewModel
import hu.bme.aut.android.securityapp.feature.mainmenu.MainMenuScreen
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
    }