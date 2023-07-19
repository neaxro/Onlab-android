package hu.bme.aut.android.securityapp.data.model.wage

import hu.bme.aut.android.securityapp.constants.LoggedPerson

data class Wage(
    val id: Int = 0,
    val name: String = "",
    val price: Double = 0.0,
    val jobName: String = ""
)

fun Wage.asUpdate(): WageUpdate{
    return WageUpdate(
        name = this.name,
        price = this.price
    )
}

fun Wage.asCreate(): WageCreate{
    return WageCreate(
        jobId = LoggedPerson.CURRENT_JOB_ID,
        name = this.name,
        price = this.price
    )
}