package hu.bme.aut.android.securityapp.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.securityapp.ui.navigation.Screen

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
            LoginScreen(navController)
        }

        composable(route = Screen.Register.route){
            RegisterScreen(navController)
        }
    }