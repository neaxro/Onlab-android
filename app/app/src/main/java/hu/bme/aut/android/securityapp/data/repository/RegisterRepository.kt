package hu.bme.aut.android.securityapp.data.repository

import hu.bme.aut.android.securityapp.data.model.people.PersonDetail
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface RegisterRepository {
    suspend fun registerUser(registerData: PersonDetail): Resource<PersonDetail>
}