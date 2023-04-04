package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.data.model.RegisterData
import hu.bme.aut.android.securityapp.data.remote.LoginApi
import hu.bme.aut.android.securityapp.data.remote.RegisterApi
import hu.bme.aut.android.securityapp.domain.repository.RegisterRepository

class RegisterRepositoryImpl(
    private val api: RegisterApi,
    private val appContext: Application
): RegisterRepository {

    override suspend fun registerUser(registerData: RegisterData): String {
        val response = api.register(registerData)

        if(response.code() == 201){
            return "Success"
        }

        return ""
    }
}