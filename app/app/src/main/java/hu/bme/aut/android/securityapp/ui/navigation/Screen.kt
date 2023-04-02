package hu.bme.aut.android.securityapp.ui.navigation

sealed class Screen(val route: String){
    object Login : Screen(route = "login")
    object Register : Screen(route = "register")
}
