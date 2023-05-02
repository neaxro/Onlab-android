package hu.bme.aut.android.securityapp.domain.repository

import hu.bme.aut.android.securityapp.data.model.dashboard.Dashboard
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface DashboardRepository {
    suspend fun getAllDashboards(jobId: Int, personId: Int): Resource<List<Dashboard>>
}