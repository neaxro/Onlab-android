package hu.bme.aut.android.securityapp.data.model.dashboard

data class Dashboard(
    val id: Int,
    val title: String,
    val message: String,
    val creationTime: String,
    val creatorName: String,
    val creatorProfilePicture: String?,
    val groupId: Int,
    val groupName: String
)