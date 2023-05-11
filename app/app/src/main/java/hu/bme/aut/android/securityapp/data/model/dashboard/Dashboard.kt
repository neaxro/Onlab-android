package hu.bme.aut.android.securityapp.data.model.dashboard

import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.ui.model.DashboardUi

data class Dashboard(
    val id: Int = 0,
    val title: String = "",
    val message: String = "",
    val creationTime: String = "",
    val creatorName: String = "",
    val creatorProfilePicture: String? = null,
    val groupId: Int = 0,
    val groupName: String = ""
)

fun Dashboard.getCreationTime(): String{
    val sliced = creationTime.split(' ')
    return sliced[sliced.lastIndex]
}

fun Dashboard.getCreationDate(): String{
    val sliced = creationTime.split(' ')
    return "${sliced[0]} ${sliced[1]}"
}

fun Dashboard.asCreateDashboardData(): CreateDashboardData{
    return CreateDashboardData(
        title = title,
        message = message,
        jobid = LoggedPerson.CURRENT_JOB_ID,
        creatorId = LoggedPerson.ID,
        groupId = groupId
    )
}

fun Dashboard.asDashboardUi(): DashboardUi{
    return DashboardUi(
        id = id,
        title = title,
        message = message,
        creationTime = creationTime,
        creatorName = creatorName,
        creatorProfilePicture = creatorProfilePicture,
        wage = Wage(id = groupId, name = groupName)
    )
}

fun Dashboard.asUpdateDashboardData(): UpdateDashboardData{
    return UpdateDashboardData(
        title = title,
        message = message,
        groupId = groupId,
    )
}