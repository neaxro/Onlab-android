package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.data.remote.WageApi
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

class WageRepositoryImpl constructor(
    private val api: WageApi,
    private val app: Application
): WageRepository {

    override suspend fun getCategories(jobId: Int): Resource<List<Wage>> {
        val connection = try{
            val result = api.getCategories(jobId = jobId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Succesfully connected to job!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch(e: Exception){
            Resource.Error("Network error occured: ${e.message}")
        }

        return connection
    }

    override suspend fun getWages(jobId: Int): Resource<List<Wage>> {
        val connection = try{
            val result = api.getWages(jobId = jobId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Succesfully connected to job!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch(e: Exception){
            Resource.Error("Network error occured: ${e.message}")
        }

        return connection
    }
}