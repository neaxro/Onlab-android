package hu.bme.aut.android.securityapp.domain.repository

import hu.bme.aut.android.securityapp.data.model.job.CreateJobData
import hu.bme.aut.android.securityapp.data.model.job.Job
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface JobRepository {
    suspend fun getAllJobForPerson(personId: Int): Resource<List<Job>>

    suspend fun createJob(createJobData: CreateJobData): Resource<Job>
}