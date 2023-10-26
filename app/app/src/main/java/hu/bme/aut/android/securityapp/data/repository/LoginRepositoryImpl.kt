package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.data.model.people.LoginData
import hu.bme.aut.android.securityapp.data.model.people.LoginResponse
import hu.bme.aut.android.securityapp.data.remote.LoginApi
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

class LoginRepositoryImpl(
    private val api: LoginApi,
    private val app: Application
): LoginRepository {

    private val context = app.applicationContext

    override suspend fun loginPerson(loginData: LoginData): Resource<LoginResponse> {

        val result = try{
            val response = api.login(loginData)

            // Check server response
            val res = if(response.code() == 200){
                    Resource.Success(message = context.getString(R.string.repository_message_successfully_logged_in), data = response.body()!!)
                }
                else{
                    // Server error
                    Resource.Error(message = response.errorBody()!!.string())
                }

            res
        } catch (e: Exception){
            // Network error
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return result
    }
}