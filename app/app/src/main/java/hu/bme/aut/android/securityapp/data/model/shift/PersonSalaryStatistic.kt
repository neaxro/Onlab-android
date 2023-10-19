package hu.bme.aut.android.securityapp.data.model.shift

data class PersonSalaryStatistic(
    val personId: Int = 0,
    val personName: String = "",
    val profilePicture: String? = null,
    val fullSalary: Double = 0.0,
)