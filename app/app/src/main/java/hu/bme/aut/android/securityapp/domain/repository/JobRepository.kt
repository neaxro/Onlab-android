package hu.bme.aut.android.securityapp.domain.repository

import hu.bme.aut.android.securityapp.data.model.job.JobResponse
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface JobRepository {
    suspend fun getAllJobForPerson(personId: Int): Resource<List<JobResponse>>
}