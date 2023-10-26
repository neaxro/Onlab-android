package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.data.model.people.PersonDefault
import hu.bme.aut.android.securityapp.data.remote.RegisterApi
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

class RegisterRepositoryImpl(
    private val api: RegisterApi,
    private val appContext: Application
): RegisterRepository {

    private val context = appContext.applicationContext

    override suspend fun registerUser(registerData: PersonDefault): Resource<PersonDefault> {
        val result = try{
            val response = api.register(registerData)
            // Check server response
            val res = if(response.code() == 201) {
                Resource.Success(message = context.getString(R.string.repository_message_successful_registration), data = registerData)
            }
            // Server error
            else{
                Resource.Error(message = response.errorBody()!!.string())
            }
            res
        }
        // Network error
        catch (e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return result
    }
}