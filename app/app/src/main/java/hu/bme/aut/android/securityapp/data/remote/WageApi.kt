package hu.bme.aut.android.securityapp.data.remote

import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.data.model.wage.WageCreate
import hu.bme.aut.android.securityapp.data.model.wage.WageUpdate
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface WageApi {
    @GET("/api/wage/categories/{jobId}")
    suspend fun getCategories(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("jobId") jobId: Int,
    ): Response<List<Wage>>

    @GET("/api/wage/forjob/{jobId}")
    suspend fun getWages(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("jobId") jobId: Int,
    ): Response<List<Wage>>

    @GET("/api/wage/{wageId}")
    suspend fun getWage(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("wageId") wageId: Int,
    ): Response<Wage>

    @PATCH("/api/wage/{wageId}")
    suspend fun updateWage(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("wageId") wageId: Int,
        @Body wageData: WageUpdate
    ): Response<Wage>

    @DELETE("/api/wage/{wageId}")
    suspend fun deleteWage(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("wageId") wageId: Int,
    ): Response<ResponseBody>

    @POST("api/wage/")
    suspend fun createWage(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Body wageData: WageCreate
    ): Response<Wage>
}