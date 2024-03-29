package hu.bme.aut.android.securityapp.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.android.securityapp.ui.feature.connecttojob.ConnectToJobScreen
import hu.bme.aut.android.securityapp.ui.feature.createJob.CreateJobScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.MainMenuScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.dashboard.CreateDashboardMessageScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.dashboard.DashboardDetailScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.jobs.JobDetailScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.jobs.JobEditScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.jobstatistics.JobStatisticsScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.pendingshifts.DetailShiftScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.pendingshifts.EditShiftScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.pendingshifts.PendingShiftsScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.people.PeopleScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.people.PersonDetailedScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.profile.ProfileScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.statistics.StatisticsScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.wages.CreateWageScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.wages.WageDetailScreen
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.wages.WagesScreen
import hu.bme.aut.android.securityapp.ui.navigation.Screen
import hu.bme.aut.android.securityapp.ui.navigation.withArgs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.fullRoute
) =
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(
            route = Screen.Login.fullRoute
        ) {
            LoginScreen(
                onSuccessLogin = {
                    navController.navigate(Screen.MainMenu.fullRoute) {
                        popUpTo(Screen.Login.fullRoute) {
                            inclusive = true
                        }
                    }
                },
                navigateToRegister = {
                    navController.navigate(Screen.Register.fullRoute)
                }
            )
        }

        composable(
            route = Screen.Register.fullRoute
        ) {
            RegisterScreen(
                navigateToLogin = {
                    navController.navigate(Screen.Login.fullRoute)
                }
            )
        }

        composable(
            route = Screen.MainMenu.fullRoute
        ) {
            MainMenuScreen(navController = navController)
        }

        composable(
            route = Screen.ConnectToJob.fullRoute
        ) {
            ConnectToJobScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.CreateJob.fullRoute
        ) {
            CreateJobScreen(
                navigateToMainMenu = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.JobDetail.fullRoute,
            arguments = listOf(
                navArgument("jobId") { type = NavType.IntType }
            )
        ) {
            JobDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                navigateEditJob = { jobId ->
                    navController.navigate(Screen.JobEdit.withArgs(jobId.toString()))
                }
            )
        }

        composable(
            route = Screen.CreateDashboardMessage.fullRoute
        ) {
            CreateDashboardMessageScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.DashboardDetails.fullRoute,
            arguments = listOf(
                navArgument("messageId") { type = NavType.IntType }
            ),
        ) {
            DashboardDetailScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Profile.fullRoute,
        ) {
            ProfileScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                onLogOut = {
                    navController.navigate(Screen.Login.fullRoute) {
                        popUpTo(Screen.MainMenu.fullRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.Wages.fullRoute,
        ) {
            WagesScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToCreate = {
                    navController.navigate(Screen.WageCreate.fullRoute)
                },
                navigateToDetails = { wageId ->
                    navController.navigate(Screen.WageDetail.withArgs(wageId.toString()))
                }
            )
        }

        composable(
            route = Screen.WageDetail.fullRoute,
            arguments = listOf(
                navArgument("wageId") { type = NavType.IntType }
            )
        ) {
            WageDetailScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.WageCreate.fullRoute
        ) {
            CreateWageScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.People.fullRoute
        ) {
            PeopleScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToDetail = { personId ->
                    navController.navigate(Screen.PeopleDetail.withArgs(personId.toString()))
                }
            )
        }

        composable(
            route = Screen.PeopleDetail.fullRoute,
            arguments = listOf(
                navArgument("personId") { type = NavType.IntType }
            )
        ) {
            PersonDetailedScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.PendingShifts.fullRoute
        ) {
            PendingShiftsScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToDetailShift = { shiftId ->
                    navController.navigate(Screen.DetailShifts.withArgs(shiftId.toString()))
                }
            )
        }

        composable(
            route = Screen.DetailShifts.fullRoute,
            arguments = listOf(
                navArgument("shiftId") { type = NavType.IntType }
            )
        ) {
            DetailShiftScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                editShift = { shiftId ->
                    navController.navigate(Screen.EditShift.withArgs(shiftId.toString()))
                }
            )
        }

        composable(
            route = Screen.Statistics.fullRoute,
        ) {
            StatisticsScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.EditShift.fullRoute,
            arguments = listOf(
                navArgument("shiftId") { type = NavType.IntType }
            )
        ) {
            EditShiftScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.JobEdit.fullRoute,
            arguments = listOf(
                navArgument("jobId"){ type = NavType.IntType }
            )
        ){
            JobEditScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.JobStatistics.fullRoute,
        ){
            JobStatisticsScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }