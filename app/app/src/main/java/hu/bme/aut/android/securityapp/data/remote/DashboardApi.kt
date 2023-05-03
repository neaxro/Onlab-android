package hu.bme.aut.android.securityapp.data.remote

import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.dashboard.CreateDashboardData
import hu.bme.aut.android.securityapp.data.model.dashboard.Dashboard
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DashboardApi {

    @GET("/api/dashboard/forperson/bypersonId/{jobId}/{personId}")
    suspend fun getAllDashboardsForPerson(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("jobId") jobId: Int,
        @Path("personId") personId: Int,
    ): Response<List<Dashboard>>

    @POST("/api/dashboard")
    suspend fun insertDashboard(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Body dashboard: CreateDashboardData
    ): Response<Dashboard>
}