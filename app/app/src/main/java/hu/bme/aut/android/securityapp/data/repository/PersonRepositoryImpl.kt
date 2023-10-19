package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.data.model.people.PersonDefault
import hu.bme.aut.android.securityapp.data.remote.PersonApi
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class PersonRepositoryImpl constructor(
    private val api: PersonApi,
    private val app: Application,
): PersonRepository {
    override suspend fun getPerson(personId: Int): Resource<Person> {
        val connection = try {
            val result = api.getPerson(personId = personId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Person successfully queried!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error("Network error occurred: ${e.message}")
        }

        return connection
    }

    override suspend fun updatePerson(personId: Int, person: PersonDefault): Resource<PersonDefault> {
        val connection = try {
            val result = api.updatePerson(personId = personId, person = person)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Person successfully updated!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error("Network error occurred: ${e.message}")
        }

        return connection
    }

    override suspend fun uploadProfilePicture(
        personId: Int,
        imageUri: Uri,
        context: Context
    ): Resource<Unit> {

        val tempUri = contentUriToFile(context,imageUri)
        val file = File(tempUri!!.path!!)
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val connection = try {
            val result = api.uploadProfilePicture(personId = personId, image = imagePart)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Profile picture successfully uploaded!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error("Network error occurred: ${e.message}")
        }

        return connection
    }

    override suspend fun getProfilePicture(personId: Int): Resource<String?> {
        val connection = try {
            val result = api.getProfilePicture(personId = personId)

            val data: Resource<String?> = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Person's profile picture successfully queried!", data = result.body()!!)
            }
            else if(result.isSuccessful && result.code() == 204){
                Resource.Success(message = "Person's profile picture successfully queried!", data = null)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error("Network error occurred: ${e.message}")
        }

        return connection
    }

    private fun contentUriToFile(context: Context, contentUri: Uri): Uri? {
        val contentResolver: ContentResolver = context.contentResolver
        val cacheDir = context.externalCacheDir ?: context.cacheDir
        val outputFile = File(cacheDir, "temp_file.jpg")

        try {
            contentResolver.openInputStream(contentUri)?.use { inputStream: InputStream ->
                FileOutputStream(outputFile).use { outputStream ->
                    val buffer = ByteArray(4 * 1024)
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                    }
                    outputStream.flush()
                }
            }

            return Uri.fromFile(outputFile)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}