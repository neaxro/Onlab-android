package hu.bme.aut.android.securityapp.data.model.wage

data class WageCreate(
    val jobId: Int = 0,
    val name: String = "",
    val price: Double = 1300.0
)