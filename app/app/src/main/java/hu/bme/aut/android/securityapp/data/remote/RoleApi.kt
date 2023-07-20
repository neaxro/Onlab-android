package hu.bme.aut.android.securityapp.data.remote

import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.role.Role
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RoleApi {
    @GET("/api/role")
    suspend fun getAllRoles(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}"
    ): Response<List<Role>>

    @GET("/api/role/{roleId}")
    suspend fun getRoleById(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("roleId") roleId: Int,
    ): Response<Role>
}