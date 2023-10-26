package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.R
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

    private val context = app.applicationContext

    override suspend fun getAllJobForPerson(personId: Int): Resource<List<DetailedJob>> {
        val jobs = try{
            val result = api.getAllJobForPerson(personId = personId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(
                    R.string.repository_message_all_jobs_successfully_queried_for_person_with_id,
                    personId.toString()
                ), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
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
                Resource.Success(message = context.getString(R.string.repository_message_person_data_successfully_queried), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return personData
    }

    override suspend fun createJob(createJobData: CreateJobData): Resource<Job> {
        val createdJob = try{
            val result = api.createJob(createJobData = createJobData)

            val data = if(result.isSuccessful && result.code() == 201){
                Resource.Success(message = context.getString(R.string.repository_message_job_successfully_created), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return createdJob
    }

    override suspend fun connectPersonToJob(personId: Int, pin: String): Resource<DetailedJob> {
        val connection = try{
            val result = api.connectPersonToJob(personId = personId, jobPin = pin)

            val data = if(result.isSuccessful && result.code() == 201){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_connected_to_job), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch(e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return connection
    }

    override suspend fun getJobById(jobId: Int): Resource<DetailedJob> {
        val connection = try {
            val result = api.getJobById(jobId = jobId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(
                    R.string.repository_message_job_successfully_queried_with_id,
                    jobId.toString()
                ), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return connection
    }

    override suspend fun selectJob(jobId: Int, personId: Int): Resource<JobSelectToken> {
        val selectJobData = SelectJobData(jobId, personId)

        val token = try {
            val result = api.selectJob(selectJobData = selectJobData)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_job_successfully_selected), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return token
    }

    override suspend fun getAllPersonOnJob(jobId: Int): Resource<List<PersonDetail>> {
        val connection = try {
            val result = api.getAllPersonOnJob(jobId = jobId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(
                    R.string.repository_message_all_person_successfully_queried_in_job_with_id,
                    jobId.toString()
                ), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
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
                Resource.Success(message = context.getString(R.string.repository_message_wage_successfully_changed_for_person), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
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
                Resource.Success(message = context.getString(R.string.repository_message_role_successfully_changed_for_person), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return connection
    }

    override suspend fun updateJob(jobId: Int, updateJobData: UpdateJobData): Resource<Job> {
        val connection = try {
            val result = api.updateJob(jobId = jobId, updateJobData = updateJobData)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_job_successfully_updated), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch (e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return connection
    }
}