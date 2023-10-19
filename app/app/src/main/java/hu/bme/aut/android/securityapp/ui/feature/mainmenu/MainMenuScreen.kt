package hu.bme.aut.android.securityapp.ui.feature.mainmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.securityapp.ui.feature.common.MyBottomNavigationBar
import hu.bme.aut.android.securityapp.ui.feature.common.MySnackbarHost
import hu.bme.aut.android.securityapp.ui.navigation.Screen

data class NavigationItem(
    val id: Int = 0,
    val name: String,
    val screen: Screen,
    val icon: ImageVector,
    val badgeCount: Int
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    viewModel: MainMenuViewModel = hiltViewModel(),
    navController: NavController
){
    val mainMenuNavController = rememberNavController()

    val bottomNavigationItems = viewModel.bottomNavigationItems.collectAsState().value

    Scaffold(
        bottomBar = {
           MyBottomNavigationBar(
               items = bottomNavigationItems,
               mainMenuNavController = mainMenuNavController,
               modifier = Modifier,
               onItemClicked = {
                   mainMenuNavController.navigate(it.screen.baseRoute)
               },
           )
        },
        snackbarHost = {
            MySnackbarHost(
                screenState = viewModel.screenState,
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(bottom = paddingValues.calculateBottomPadding())
        ){
            MainMenuNavHost(
                navController = navController,
                mainMenuNavController = mainMenuNavController
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowBottomNavBar(){
    MainMenuScreen(
        navController =  rememberNavController()
    )
}