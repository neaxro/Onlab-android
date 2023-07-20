package hu.bme.aut.android.securityapp.data.model.people

import hu.bme.aut.android.securityapp.data.model.role.Role
import hu.bme.aut.android.securityapp.data.model.wage.Wage

data class PersonDetail(
    val basicInfo: Person = Person(),
    val role: Role = Role(),
    val wage: Wage = Wage()
)