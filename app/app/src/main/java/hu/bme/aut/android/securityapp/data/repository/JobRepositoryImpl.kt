package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.data.model.job.ChangeRoleData
import hu.bme.aut.android.securityapp.data.model.job.ChangeWageData
import hu.bme.aut.android.securityapp.data.model.job.CreateJobData
import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.model.job.Job
import hu.bme.aut.android.securityapp.data.model.job.JobSelectToken
import hu.bme.aut.android.securityapp.data.model.job.SelectJobData
import hu.bme.aut.android.securityapp.data.model.job.UpdateJobData
import hu.bme.aut.android.securityapp.data.model.people.PersonDetail
import hu.bme.aut.android.securityapp.data.remote.JobApi
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import okhttp3.ResponseBody

class JobRepositoryImpl (
    private val api: JobApi,
    private val app: Application
): JobRepository {

    override suspend fun getAllJobForPerson(personId: Int): Resource<List<DetailedJob>> {
        val jobs = try{
            val result = api.getAllJobForPerson(personId = personId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "All jobs successfully queried for person with ID: $personId!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(message = "Network error occurred: ${e.message}")
        }

        return jobs
    }

    override suspend fun getDetailedPersonDataInJob(
        jobId: Int,
        personId: Int
    ): Resource<PersonDetail> {
        val personData = try{
            val result = api.getDetailedPersonDataInJob(jobId = jobId, personId = personId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Person data successfully queried!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(message = "Network error occurred: ${e.message}")
        }

        return personData
    }

    override suspend fun createJob(createJobData: CreateJobData): Resource<Job> {
        val createdJob = try{
            val result = api.createJob(createJobData = createJobData)

            val data = if(result.isSuccessful && result.code() == 201){
                Resource.Success(message = "Job successfully created!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(message = "Network error occurred: ${e.message}")
        }

        return createdJob
    }

    override suspend fun connectPersonToJob(personId: Int, pin: String): Resource<DetailedJob> {
        val connection = try{
            val result = api.connectPersonToJob(personId = personId, jobPin = pin)

            val data = if(result.isSuccessful && result.code() == 201){
                Resource.Success(message = "Successfully connected to job!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch(e: Exception){
            Resource.Error("Network error occurred: ${e.message}")
        }

        return connection
    }

    override suspend fun getJobById(jobId: Int): Resource<DetailedJob> {
        val connection = try {
            val result = api.getJobById(jobId = jobId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Job successfully queried with ID: $jobId!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error("Network error occurred: ${e.message}")
        }

        return connection
    }

    override suspend fun selectJob(jobId: Int, personId: Int): Resource<JobSelectToken> {
        val selectJobData = SelectJobData(jobId, personId)

        val token = try {
            val result = api.selectJob(selectJobData = selectJobData)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Job successfully selected!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error("Network error occurred: ${e.message}")
        }

        return token
    }

    override suspend fun getAllPersonOnJob(jobId: Int): Resource<List<PersonDetail>> {
        val connection = try {
            val result = api.getAllPersonOnJob(jobId = jobId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "All person successfully queried in job with ID: $jobId!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error("Network error occurred: ${e.message}")
        }

        return connection
    }

    override suspend fun changeWageForPerson(
        jobId: Int,
        changeWageData: ChangeWageData
    ): Resource<ResponseBody> {
        val connection = try {
            val result = api.changeWageForPerson(jobId = jobId, changeWageData = changeWageData)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Wage successfully changed for person!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error("Network error occurred: ${e.message}")
        }

        return connection
    }

    override suspend fun changeRoleForPerson(
        jobId: Int,
        changeRoleData: ChangeRoleData
    ): Resource<ResponseBody> {
        val connection = try {
            val result = api.changeRoleForPerson(jobId = jobId, changeRoleData = changeRoleData)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Role successfully changed for person!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error("Network error occurred: ${e.message}")
        }

        return connection
    }

    override suspend fun updateJob(jobId: Int, updateJobData: UpdateJobData): Resource<Job> {
        val connection = try {
            val result = api.updateJob(jobId = jobId, updateJobData = updateJobData)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Job successfully updated!", data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error("Network error occurred: ${e.message}")
        }

        return connection
    }
}