package hu.bme.aut.android.securityapp.data.remote

import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.job.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

// TODO: Visszatérési típusokkal folytatni

interface JobApi {

    @GET("/api/job/availablefor/{personId}")
    suspend fun getAllJobForPerson(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("personId") personId: Int,
    ): Response<List<DetailedJob>>

    @GET("/api/job/{jobId}/detail")
    suspend fun getJobById(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("jobId") jobId: Int
    ): Response<DetailedJob>

    @POST("/api/job")
    suspend fun createJob(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Body createJobData: CreateJobData
    ): Response<Job>

    @POST("/api/job/connect/{jobPin}/{personId}")
    suspend fun connectPersonToJob(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("jobPin") jobPin: String,
        @Path("personId") personId: Int
    ): Response<DetailedJob>

    @POST("/api/job/select")
    suspend fun selectJob(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Body selectJobData: SelectJobData
    ): Response<JobSelectToken>

    @PATCH("/api/job/changerole/{jobId}")
    suspend fun changeRoleForPerson(@Path("jobId") jobId: Int, @Body changeRoleData: ChangeRoleData)

    @PATCH("/api/job/changewage/{jobId}")
    suspend fun changeWageForPerson(@Path("jobId") jobId: Int, @Body changeWageData: ChangeWageData)

    @DELETE("/api/job/{jobId}")
    suspend fun deleteJob(@Path("jobId") jobId: Int)
}