package hu.bme.aut.android.securityapp.ui.navigation

sealed class Screen(val route: String){
    object Login : Screen(route = "login_screen")
    object Register : Screen(route = "register_screen")
    object NoJob: Screen(route = "nojob_screen")
    object CreateJob: Screen(route = "createjob_screen")
    object MainMenu: Screen(route = "mainmenu_screen")
    object ConnectToJob: Screen(route = "connecttojob_screen")

    object Jobs: Screen(route = "jobs_screen")
    object JobDetail: Screen(route = "jobdetail_screen/{jobId}")
    object Dashboard: Screen(route = "dashboard_screen")
    object CreateDashboardMessage: Screen(route = "create_dashboard_screen")
    object Shift: Screen(route = "shift_screen")
    object Statistics: Screen(route = "statistics_screen")
}
