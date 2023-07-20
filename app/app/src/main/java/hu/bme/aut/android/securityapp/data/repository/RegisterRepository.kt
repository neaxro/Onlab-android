package hu.bme.aut.android.securityapp.data.repository

import hu.bme.aut.android.securityapp.data.model.people.PersonDefault
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface RegisterRepository {
    suspend fun registerUser(registerData: PersonDefault): Resource<PersonDefault>
}