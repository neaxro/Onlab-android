package hu.bme.aut.android.securityapp.data.model.job

data class CreateJobData(
    val title: String,
    val description: String,
    val ownerId: Int,
)