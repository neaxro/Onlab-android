package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.data.model.job.CreateJobData
import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.model.job.Job
import hu.bme.aut.android.securityapp.data.model.job.JobSelectToken
import hu.bme.aut.android.securityapp.data.model.job.SelectJobData
import hu.bme.aut.android.securityapp.data.model.people.PersonDetail
import hu.bme.aut.android.securityapp.data.remote.JobApi
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

class JobRepositoryImpl (
    private val api: JobApi,
    private val app: Application
): JobRepository {

    override suspend fun getAllJobForPerson(personId: Int): Resource<List<DetailedJob>> {
        val jobs = try{
            val result = api.getAllJobForPerson(personId = personId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Successful! Code: ${result.code()}", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(message = "Network error occured: ${e.message}")
        }

        return jobs
    }

    override suspend fun createJob(createJobData: CreateJobData): Resource<Job> {
        val createdJob = try{
            val result = api.createJob(createJobData = createJobData)

            val data = if(result.isSuccessful && result.code() == 201){
                Resource.Success(message = "Successful! Code: ${result.code()}", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(message = "Network error occured: ${e.message}")
        }

        return createdJob
    }

    override suspend fun connectPersonToJob(personId: Int, pin: String): Resource<DetailedJob> {
        val connection = try{
            val result = api.connectPersonToJob(personId = personId, jobPin = pin)

            val data = if(result.isSuccessful && result.code() == 201){
                Resource.Success(message = "Succesfully connected to job!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch(e: Exception){
            Resource.Error("Network error occured: ${e.message}")
        }

        return connection
    }

    override suspend fun getJobById(jobId: Int): Resource<DetailedJob> {
        val connection = try {
            val result = api.getJobById(jobId = jobId)

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

    override suspend fun selectJob(jobId: Int, personId: Int): Resource<JobSelectToken> {
        val selectJobData = SelectJobData(jobId, personId)

        val token = try {
            val result = api.selectJob(selectJobData = selectJobData)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Job succesfully selected!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error("Network error occured: ${e.message}")
        }

        return token
    }

    override suspend fun getAllPersonOnJob(jobId: Int): Resource<List<PersonDetail>> {
        val connection = try {
            val result = api.getAllPersonOnJob(jobId = jobId)

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
}