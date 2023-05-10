package hu.bme.aut.android.securityapp.ui.navigation

sealed class Screen(val baseRoute: String, val fullRoute: String = baseRoute){
    object Login : Screen(baseRoute = "login_screen")
    object Register : Screen(baseRoute = "register_screen")
    object NoJob: Screen(baseRoute = "nojob_screen")
    object CreateJob: Screen(baseRoute = "createjob_screen")
    object MainMenu: Screen(baseRoute = "mainmenu_screen")
    object ConnectToJob: Screen(baseRoute = "connecttojob_screen")

    object Jobs: Screen(baseRoute = "jobs_screen")
    object JobDetail: Screen(baseRoute = "jobdetail_screen", fullRoute = "jobdetail_screen/{jobId}")
    object Dashboard: Screen(baseRoute = "dashboard_screen")
    object CreateDashboardMessage: Screen(baseRoute = "create_dashboard_screen")
    object DashboardDetails: Screen(baseRoute = "dashboard_details_screen", fullRoute = "dashboard_details_screen/{messageId}")
    object Shift: Screen(baseRoute = "shift_screen")
    object Statistics: Screen(baseRoute = "statistics_screen")
}

fun Screen.withArgs(vararg args: String): String{
    return buildString {
        append(baseRoute)
        args.forEach { arg ->
            append("/$arg")
        }
    }
}