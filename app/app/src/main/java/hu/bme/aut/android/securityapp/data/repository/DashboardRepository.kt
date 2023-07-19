package hu.bme.aut.android.securityapp.data.repository

import hu.bme.aut.android.securityapp.data.model.dashboard.CreateDashboardData
import hu.bme.aut.android.securityapp.data.model.dashboard.Dashboard
import hu.bme.aut.android.securityapp.data.model.dashboard.UpdateDashboardData
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import okhttp3.ResponseBody

interface DashboardRepository {
    suspend fun getAllDashboards(jobId: Int, personId: Int): Resource<List<Dashboard>>

    suspend fun getDashboard(dashboardId: Int): Resource<Dashboard>

    suspend fun createDashboard(dashboard: CreateDashboardData): Resource<Dashboard>

    suspend fun updateDashboard(dashboardId: Int, dashboardData: UpdateDashboardData): Resource<Dashboard>

    suspend fun deleteDashboard(dashboardId: Int): Resource<ResponseBody>
}