package hu.bme.aut.android.securityapp.data.model.dashboard

data class CreateDashboardData(
    val title: String,
    val message: String,
    val jobid: Int,
    val creatorId: Int,
    val groupId: Int
)