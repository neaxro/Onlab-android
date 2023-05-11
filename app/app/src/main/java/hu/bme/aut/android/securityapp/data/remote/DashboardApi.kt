package hu.bme.aut.android.securityapp.data.remote

import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.dashboard.CreateDashboardData
import hu.bme.aut.android.securityapp.data.model.dashboard.Dashboard
import hu.bme.aut.android.securityapp.data.model.dashboard.UpdateDashboardData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface DashboardApi {

    @GET("/api/dashboard/forperson/bypersonId/{jobId}/{personId}")
    suspend fun getAllDashboardsForPerson(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("jobId") jobId: Int,
        @Path("personId") personId: Int,
    ): Response<List<Dashboard>>

    @GET("/api/dashboard/{dashboardId}")
    suspend fun getDashboard(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("dashboardId") dashboardId: Int,
    ): Response<Dashboard>

    @POST("/api/dashboard")
    suspend fun insertDashboard(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Body dashboard: CreateDashboardData
    ): Response<Dashboard>

    @PATCH("/api/dashboard/{dashboardId}")
    suspend fun updateDashboard(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("dashboardId") dashboardId: Int,
        @Body updateDashboardData: UpdateDashboardData,
    ): Response<Dashboard>

    @DELETE("/api/dashboard/{dashboardId}")
    suspend fun deleteDashboard(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("dashboardId") dashboardId: Int,
    ): Response<ResponseBody>
}