package hu.bme.aut.android.securityapp.domain.repository

import hu.bme.aut.android.securityapp.data.model.RegisterData
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface RegisterRepository {
    suspend fun registerUser(registerData: RegisterData): Resource<RegisterData>
}