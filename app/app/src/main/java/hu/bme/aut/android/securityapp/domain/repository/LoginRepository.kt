package hu.bme.aut.android.securityapp.domain.repository

import hu.bme.aut.android.securityapp.data.model.LoginData

interface LoginRepository {
    suspend fun loginPerson(loginData: LoginData): String
}