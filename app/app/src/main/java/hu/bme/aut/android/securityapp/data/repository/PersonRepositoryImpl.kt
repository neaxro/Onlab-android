package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.data.model.people.PersonDetail
import hu.bme.aut.android.securityapp.data.remote.PersonApi
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

class PersonRepositoryImpl constructor(
    private val api: PersonApi,
    private val app: Application,
): PersonRepository {
    override suspend fun getPerson(personId: Int): Resource<Person> {
        val connection = try {
            val result = api.getPerson(personId = personId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Person succesfully found!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error("Network error occured: ${e.message}")
        }

        return connection
    }

    override suspend fun updatePerson(personId: Int, person: PersonDetail): Resource<PersonDetail> {
        val connection = try {
            val result = api.updatePerson(personId = personId, person = person)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Person succesfully updated!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error("Network error occured: ${e.message}")
        }

        return connection
    }
}