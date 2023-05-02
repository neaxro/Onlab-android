package hu.bme.aut.android.securityapp.domain.repository

import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface WageRepository {
    suspend fun getCategories(jobId: Int): Resource<List<Wage>>
}