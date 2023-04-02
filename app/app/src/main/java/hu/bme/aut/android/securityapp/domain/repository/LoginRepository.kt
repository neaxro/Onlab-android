package hu.bme.aut.android.securityapp.domain.repository

import hu.bme.aut.android.securityapp.data.model.LoginData
import hu.bme.aut.android.securityapp.data.model.Person

interface LoginRepository {
    suspend fun GetAllPerson(): List<Person>

    suspend fun LoginPerson(loginData: LoginData): Person?
}