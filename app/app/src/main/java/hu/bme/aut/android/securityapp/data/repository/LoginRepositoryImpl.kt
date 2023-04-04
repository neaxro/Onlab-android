package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import android.widget.Toast
import hu.bme.aut.android.securityapp.data.model.LoginData
import hu.bme.aut.android.securityapp.data.model.Person
import hu.bme.aut.android.securityapp.data.model.RegisterData
import hu.bme.aut.android.securityapp.data.remote.LoginApi
import hu.bme.aut.android.securityapp.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val api: LoginApi,
    private val appContext: Application
): LoginRepository {
    override suspend fun loginPerson(loginData: LoginData): String {
        val response = api.login(loginData)

        if(response.isSuccessful){
            return response.body()!!.token
        }
        return ""
    }
}