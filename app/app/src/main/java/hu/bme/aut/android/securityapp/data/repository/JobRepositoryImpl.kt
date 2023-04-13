package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.data.model.job.CreateJobData
import hu.bme.aut.android.securityapp.data.model.job.Job
import hu.bme.aut.android.securityapp.data.remote.JobApi
import hu.bme.aut.android.securityapp.domain.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

class JobRepositoryImpl (
    private val api: JobApi,
    private val app: Application
): JobRepository{

    override suspend fun getAllJobForPerson(personId: Int): Resource<List<Job>> {
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
            var result = api.createJob(createJobData = createJobData)

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
}