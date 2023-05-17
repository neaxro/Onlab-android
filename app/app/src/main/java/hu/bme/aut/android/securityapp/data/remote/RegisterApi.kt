package hu.bme.aut.android.securityapp.data.remote

import hu.bme.aut.android.securityapp.data.model.people.PersonDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @POST("/api/people/register")
    suspend fun register(@Body registerData: PersonDetail): Response<Unit>
}