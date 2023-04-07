package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.data.model.people.RegisterData
import hu.bme.aut.android.securityapp.data.remote.RegisterApi
import hu.bme.aut.android.securityapp.domain.repository.RegisterRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

class RegisterRepositoryImpl(
    private val api: RegisterApi,
    private val appContext: Application
): RegisterRepository {

    override suspend fun registerUser(registerData: RegisterData): Resource<RegisterData> {
        val result = try{
            val response = api.register(registerData)
            // Check server response
            val res = if(response.code() == 201) {
                Resource.Success(
                    data = registerData,
                    message = "Successul, Code: ${response.code()}"
                )
            }
            // Server error
            else{
                Resource.Error(message = "Server error: ${response.errorBody()!!.string()}")
            }
            res
        }
        // Network error
        catch (e: Exception){
            Resource.Error("Network error occured: ${e.message}")
        }

        return result
    }
}