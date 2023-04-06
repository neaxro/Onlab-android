package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import android.widget.Toast
import hu.bme.aut.android.securityapp.data.model.LoginData
import hu.bme.aut.android.securityapp.data.model.LoginResponse
import hu.bme.aut.android.securityapp.data.model.Person
import hu.bme.aut.android.securityapp.data.model.RegisterData
import hu.bme.aut.android.securityapp.data.remote.LoginApi
import hu.bme.aut.android.securityapp.domain.repository.LoginRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

class LoginRepositoryImpl(
    private val api: LoginApi,
    private val appContext: Application
): LoginRepository {
    override suspend fun loginPerson(loginData: LoginData): Resource<LoginResponse> {

        val result = try{
            val response = api.login(loginData)

            // Check server response
            val res = if(response.code() == 200){
                    Resource.Success(data = response.body()!!, message = "Succesfull, ID: ${response.code()}")
                }
                else{
                    // Server error
                    Resource.Error(message = "Server error occured: ${response.errorBody()!!.string()}")
                }

            res
        } catch (e: Exception){
            // Network error
            Resource.Error(message = "Network error occured: ${e.message}")
        }

        return result
    }
}