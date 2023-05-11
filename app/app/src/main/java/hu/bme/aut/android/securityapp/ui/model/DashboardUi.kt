package hu.bme.aut.android.securityapp.ui.model

import hu.bme.aut.android.securityapp.data.model.dashboard.Dashboard
import hu.bme.aut.android.securityapp.data.model.wage.Wage

data class DashboardUi(
    val id: Int = 0,
    val title: String = "",
    val message: String = "",
    val creationTime: String = "",
    val creatorName: String = "",
    val creatorProfilePicture: String? = null,
    val wage: Wage = Wage(),
)

fun DashboardUi.toDashboard(): Dashboard{
    return Dashboard(
        id = id,
        title = title,
        message = message,
        creationTime = creationTime,
        creatorName = creatorName,
        creatorProfilePicture = creatorProfilePicture,
        groupId = wage.id,
        groupName = wage.name,
    )
}
