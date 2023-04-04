package hu.bme.aut.android.securityapp.domain.repository

import hu.bme.aut.android.securityapp.data.model.RegisterData

interface RegisterRepository {
    suspend fun registerUser(registerData: RegisterData): String
}