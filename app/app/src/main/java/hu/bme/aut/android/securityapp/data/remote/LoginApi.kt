package hu.bme.aut.android.securityapp.data.remote

import hu.bme.aut.android.securityapp.data.model.LoginData
import hu.bme.aut.android.securityapp.data.model.Person
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {

    @GET("/api/people")
    suspend fun getAllPeople(): Response<List<Person>>

    @POST("/api/people/login")
    suspend fun login(@Body loginData: LoginData): Response<Person>
}