package hu.bme.aut.android.securityapp.data.remote

import hu.bme.aut.android.securityapp.data.model.people.LoginData
import hu.bme.aut.android.securityapp.data.model.people.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/api/people/login")
    suspend fun login(@Body loginData: LoginData): Response<LoginResponse>
}