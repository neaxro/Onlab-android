package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.data.model.people.PersonDefault
import hu.bme.aut.android.securityapp.data.remote.RegisterApi
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

class RegisterRepositoryImpl(
    private val api: RegisterApi,
    private val appContext: Application
): RegisterRepository {

    override suspend fun registerUser(registerData: PersonDefault): Resource<PersonDefault> {
        val result = try{
            val response = api.register(registerData)
            // Check server response
            val res = if(response.code() == 201) {
                Resource.Success(message = "Successful registration!", data = registerData)
            }
            // Server error
            else{
                Resource.Error(message = response.errorBody()!!.string())
            }
            res
        }
        // Network error
        catch (e: Exception){
            Resource.Error("Network error occurred: ${e.message}")
        }

        return result
    }
}