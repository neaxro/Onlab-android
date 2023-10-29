package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.data.model.dashboard.CreateDashboardData
import hu.bme.aut.android.securityapp.data.model.dashboard.Dashboard
import hu.bme.aut.android.securityapp.data.model.dashboard.UpdateDashboardData
import hu.bme.aut.android.securityapp.data.remote.DashboardApi
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import okhttp3.ResponseBody

class DashboardRepositoryImpl (
    private val api: DashboardApi,
    private val app: Application
): DashboardRepository {

    private val context = app.applicationContext

    override suspend fun getAllDashboardsForPerson(jobId: Int, personId: Int): Resource<List<Dashboard>> {
        val connection = try {
            val result = api.getAllDashboardsForPerson(jobId = jobId, personId = personId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_all_dashboards_successfully_queried), data = result.body()!!)
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

    override suspend fun getDashboard(dashboardId: Int): Resource<Dashboard> {
        val connection = try {
            val result = api.getDashboard(dashboardId = dashboardId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(
                    R.string.repository_message_dashboard_with_id_successfully_queried,
                    dashboardId.toString()
                ), data = result.body()!!)
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

    override suspend fun getAllDashboardsForJob(jobId: Int): Resource<List<Dashboard>> {
        val connection = try {
            val result = api.getAllDashboardsForJob(jobId = jobId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(
                    R.string.repository_message_all_dashboards_successfully_queried_fow_jobid,
                    jobId.toString()
                ), data = result.body()!!)
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

    override suspend fun createDashboard(dashboard: CreateDashboardData): Resource<Dashboard> {
        val connection = try {
            val result = api.createDashboard(dashboard = dashboard)

            val data = if(result.isSuccessful && result.code() == 201){
                Resource.Success(message = context.getString(R.string.repository_message_dashboard_successfully_created), data = result.body()!!)
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

    override suspend fun updateDashboard(
        dashboardId: Int,
        dashboardData: UpdateDashboardData
    ): Resource<Dashboard> {
        val connection = try {
            val result = api.updateDashboard(dashboardId = dashboardId, updateDashboardData = dashboardData)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_dashboard_successfully_updated), data = result.body()!!)
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

    override suspend fun deleteDashboard(dashboardId: Int): Resource<ResponseBody> {
        val connection = try {
            val result = api.deleteDashboard(dashboardId = dashboardId)

            val data = if(result.isSuccessful && result.code() == 204){
                Resource.Success(message = context.getString(R.string.repository_message_dashboard_successfully_deleted), data = result.body()!!)
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
}