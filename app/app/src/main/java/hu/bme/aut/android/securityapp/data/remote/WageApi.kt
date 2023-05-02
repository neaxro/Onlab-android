package hu.bme.aut.android.securityapp.data.remote

import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface WageApi {
    @GET("/api/wage/categories/{jobId}")
    suspend fun getCategories(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("jobId") jobId: Int,
    ): Response<List<Wage>>
}