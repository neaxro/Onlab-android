package hu.bme.aut.android.securityapp.data.remote

import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.data.model.people.PersonDefault
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PersonApi {

    @GET("/api/people/{personId}")
    suspend fun getPerson(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("personId") personId: Int
    ): Response<Person>

    @PATCH("/api/people/{personId}")
    suspend fun updatePerson(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("personId") personId: Int,
        @Body person: PersonDefault,
    ): Response<PersonDefault>

    // form-data; name=\"image\"; filename=\"image.png\"
    @Multipart
    @POST("/api/people/picture/{personId}")
    suspend fun uploadProfilePicture(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("personId") personId: Int,
        @Part image: MultipartBody.Part
    ): Response<Unit>

    @GET("/api/people/picture/{personId}")
    suspend fun getProfilePicture(
        @Header("Authorization") token: String = "Bearer ${LoggedPerson.TOKEN}",
        @Path("personId") personId: Int,
    ): Response<String>
}