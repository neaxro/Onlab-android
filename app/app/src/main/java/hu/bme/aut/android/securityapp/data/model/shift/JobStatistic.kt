package hu.bme.aut.android.securityapp.data.model.shift

data class JobStatistic(
    val overallSalary: Double = 0.0,
    val peopleSalaryStatistics: List<PersonSalaryStatistic> = listOf()
)