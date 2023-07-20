package hu.bme.aut.android.securityapp.data.repository

import hu.bme.aut.android.securityapp.data.model.job.CreateJobData
import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.model.job.Job
import hu.bme.aut.android.securityapp.data.model.job.JobSelectToken
import hu.bme.aut.android.securityapp.data.model.people.PersonDetail
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface JobRepository {
    suspend fun getAllJobForPerson(personId: Int): Resource<List<DetailedJob>>

    suspend fun createJob(createJobData: CreateJobData): Resource<Job>

    suspend fun connectPersonToJob(personId: Int, pin: String): Resource<DetailedJob>

    suspend fun getJobById(jobId: Int): Resource<DetailedJob>

    suspend fun selectJob(jobId: Int, personId: Int): Resource<JobSelectToken>

    suspend fun getAllPersonOnJob(jobId: Int): Resource<List<PersonDetail>>
}