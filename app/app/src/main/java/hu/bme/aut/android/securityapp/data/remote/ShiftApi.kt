package hu.bme.aut.android.securityapp.data.remote

import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.shift.CreateShiftData
import hu.bme.aut.android.securityapp.data.model.shift.Shift
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ShiftApi {
    @GET("/api/shift/inprogress/{jobId}/forperson/{personId}")
    suspend fun getCurrentForPerson(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("jobId") jobId: Int,
        @Path("personId") personId: Int
    ): Response<Shift?>

    @GET("/api/shift/{shiftId}")
    suspend fun getById(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("shiftId") shiftId: Int
    ): Response<Shift?>

    @POST("/api/shift")
    suspend fun createShift(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Body shiftData: CreateShiftData
    ): Response<Shift>

    @PATCH("/api/shift/finish/{shiftId}")
    suspend fun endShift(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("shiftId") shiftId: Int
    ): Response<Shift>
}