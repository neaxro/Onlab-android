package hu.bme.aut.android.securityapp.ui.navigation

sealed class Screen(val route: String){
    object Login : Screen(route = "login_screen")
    object Register : Screen(route = "register_screen")
    object NoJob: Screen(route = "nojob_screen")
    object CreateJob: Screen(route = "createjob_screen")
    object MainMenu: Screen(route = "mainmenu_screen")
}
