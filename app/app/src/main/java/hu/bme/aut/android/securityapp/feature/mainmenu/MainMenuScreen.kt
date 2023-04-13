package hu.bme.aut.android.securityapp.feature.mainmenu

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.Leaderboard
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableOpenTarget
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.DashboardScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.JobsScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.ShiftScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.StatisticsScreen
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.jobs.JobsViewModel
import hu.bme.aut.android.securityapp.ui.navigation.Screen

data class NavigationItem(
    val name: String,
    val screen: Screen,
    val icon: ImageVector,
    val badgeCount: Int
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(){
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        NavigationItem(name = "Jobs", screen = Screen.Jobs, icon = Icons.Rounded.Work, badgeCount = 0),
        NavigationItem(name = "Dashboard", screen = Screen.Dashboard, icon = Icons.Rounded.Dashboard, badgeCount = 0),
        NavigationItem(name = "Shift", screen = Screen.Shift, icon = Icons.Rounded.Shield, badgeCount = 0),
        NavigationItem(name = "Statistics", screen = Screen.Statistics, icon = Icons.Rounded.Leaderboard, badgeCount = 0),
    )

    Scaffold(
        bottomBar = {
           BottomNavigationBar(
               items = bottomNavigationItems,
               navController = navController,
               modifier = Modifier,
               onItemClicked = {
                   navController.navigate(it.screen.route)
               },
           )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(bottom = paddingValues.calculateBottomPadding())
        ){
            MainMenuNavHost(navController = navController)
        }
    }
}

@Composable
fun MainMenuNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.Jobs.route
){
    NavHost(
        modifier = modifier
            .fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ){

        composable(route = Screen.Jobs.route){
            val jobViewModel = hiltViewModel<JobsViewModel>()
            JobsScreen(jobViewModel)
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

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    navController: NavController,
    modifier: Modifier,
    onItemClicked: (NavigationItem) -> Unit
){
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = modifier,
        containerColor = Color.White,
        tonalElevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.screen.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    onItemClicked(item)
                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if(item.badgeCount > 0){
                            BadgedBox(
                                badge = {
                                    Text(text = item.badgeCount.toString())
                                }
                            ) {
                                Icon(imageVector = item.icon, contentDescription = item.name)
                            }
                        }
                        else{
                            Icon(imageVector = item.icon, contentDescription = item.name)
                        }
                    }
                },
                label = { Text(text = item.name) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun showBottomNavBar(){
    MainMenuScreen()
}