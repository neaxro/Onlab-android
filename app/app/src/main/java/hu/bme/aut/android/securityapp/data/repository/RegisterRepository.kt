package hu.bme.aut.android.securityapp.data.repository

import hu.bme.aut.android.securityapp.data.model.people.RegisterData
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface RegisterRepository {
    suspend fun registerUser(registerData: RegisterData): Resource<RegisterData>
}