package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.data.model.dashboard.CreateDashboardData
import hu.bme.aut.android.securityapp.data.model.dashboard.Dashboard
import hu.bme.aut.android.securityapp.data.remote.DashboardApi
import hu.bme.aut.android.securityapp.domain.repository.DashboardRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

class DashboardRepositoryImpl (
    private val api: DashboardApi,
    private val app: Application
): DashboardRepository {

    override suspend fun getAllDashboards( jobId: Int, personId: Int): Resource<List<Dashboard>> {
        val connection = try {
            val result = api.getAllDashboardsForPerson(jobId = jobId, personId = personId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Job succesfully found!", data = result.body()!!)
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

    override suspend fun createDashboard(dashboard: CreateDashboardData): Resource<Dashboard> {
        val connection = try {
            val result = api.insertDashboard(dashboard = dashboard)

            val data = if(result.isSuccessful && result.code() == 201){
                Resource.Success(message = "Dashboard succesfully created!", data = result.body()!!)
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