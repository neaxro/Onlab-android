package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.data.model.wage.WageCreate
import hu.bme.aut.android.securityapp.data.model.wage.WageUpdate
import hu.bme.aut.android.securityapp.data.remote.WageApi
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import okhttp3.ResponseBody

class WageRepositoryImpl constructor(
    private val api: WageApi,
    private val app: Application
): WageRepository {

    private val context = app.applicationContext

    override suspend fun getCategories(jobId: Int): Resource<List<Wage>> {
        val connection = try{
            val result = api.getCategories(jobId = jobId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_get_categories), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch(e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return connection
    }

    override suspend fun getWages(jobId: Int): Resource<List<Wage>> {
        val connection = try{
            val result = api.getWages(jobId = jobId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_queried_all_wages), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch(e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return connection
    }

    override suspend fun getWage(wageId: Int): Resource<Wage> {
        val connection = try{
            val result = api.getWage(wageId = wageId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(
                    R.string.repository_message_successfully_queried_wage_with_id,
                    wageId.toString()
                ), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch(e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return connection
    }

    override suspend fun updateWage(wageId: Int, wageData: WageUpdate): Resource<Wage> {
        val connection = try{
            val result = api.updateWage(wageId = wageId, wageData = wageData)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_updated_wage), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch(e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return connection
    }

    override suspend fun deleteWage(wageId: Int): Resource<ResponseBody> {
        val connection = try {
            val result = api.deleteWage(wageId = wageId)

            val data = if(result.isSuccessful && result.code() == 204){
                Resource.Success(message = context.getString(R.string.repository_message_wage_successfully_deleted), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return connection
    }

    override suspend fun createWage(wageData: WageCreate): Resource<Wage> {
        val connection = try{
            val result = api.createWage(wageData = wageData)

            val data = if(result.isSuccessful && result.code() == 201){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_created_a_new_wage), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch(e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return connection
    }
}