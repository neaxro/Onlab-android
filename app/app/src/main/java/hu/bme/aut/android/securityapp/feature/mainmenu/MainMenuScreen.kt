package hu.bme.aut.android.securityapp.feature.mainmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.securityapp.ui.navigation.Screen

data class NavigationItem(
    val name: String,
    val screen: Screen,
    val icon: ImageVector,
    val badgeCount: Int
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    navController: NavController
){
    val mainMenuNavController = rememberNavController()

    val bottomNavigationItems = listOf(
        NavigationItem(name = "Jobs", screen = Screen.Jobs, icon = Icons.Rounded.Work, badgeCount = 0),
        NavigationItem(name = "Dashboard", screen = Screen.Dashboard, icon = Icons.Rounded.Dashboard, badgeCount = 0),
        NavigationItem(name = "Shift", screen = Screen.Shift, icon = Icons.Rounded.Shield, badgeCount = 0),
        NavigationItem(name = "More", screen = Screen.Statistics, icon = Icons.Rounded.MoreHoriz, badgeCount = 0),
    )

    Scaffold(
        bottomBar = {
           BottomNavigationBar(
               items = bottomNavigationItems,
               mainMenuNavController = mainMenuNavController,
               modifier = Modifier,
               onItemClicked = {
                   mainMenuNavController.navigate(it.screen.baseRoute)
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
            MainMenuNavHost(
                navController = navController,
                mainMenuNavController = mainMenuNavController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    mainMenuNavController: NavController,
    modifier: Modifier,
    onItemClicked: (NavigationItem) -> Unit
){
    val backStackEntry = mainMenuNavController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = modifier,
        containerColor = Color.White,
        tonalElevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.screen.baseRoute == backStackEntry.value?.destination?.route

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
fun ShowBottomNavBar(){
    MainMenuScreen(rememberNavController())
}