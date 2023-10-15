package hu.bme.aut.android.securityapp.data.model.dashboard

data class CreateDashboardData(
    val title: String = "",
    val message: String = "",
    val jobId: Int = 0,
    val creatorId: Int = 0,
    val groupId: Int = 0,
)