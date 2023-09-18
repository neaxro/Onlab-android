package hu.bme.aut.android.securityapp.data.model.shift

data class CreateShiftData(
    val jobId: Int,
    val personId: Int,
    val wageId: Int
)