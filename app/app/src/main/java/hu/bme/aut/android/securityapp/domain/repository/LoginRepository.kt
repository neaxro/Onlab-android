package hu.bme.aut.android.securityapp.domain.repository

import hu.bme.aut.android.securityapp.data.model.people.LoginData
import hu.bme.aut.android.securityapp.data.model.people.LoginResponse
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface LoginRepository {
    suspend fun loginPerson(loginData: LoginData): Resource<LoginResponse>
}