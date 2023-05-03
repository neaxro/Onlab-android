package hu.bme.aut.android.securityapp.domain.repository

import hu.bme.aut.android.securityapp.data.model.dashboard.CreateDashboardData
import hu.bme.aut.android.securityapp.data.model.dashboard.Dashboard
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface DashboardRepository {
    suspend fun getAllDashboards(jobId: Int, personId: Int): Resource<List<Dashboard>>

    suspend fun createDashboard(dashboard: CreateDashboardData): Resource<Dashboard>
}