package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.data.model.shift.CreateShiftData
import hu.bme.aut.android.securityapp.data.model.shift.JobStatistic
import hu.bme.aut.android.securityapp.data.model.shift.Shift
import hu.bme.aut.android.securityapp.data.model.shift.UpdateShiftData
import hu.bme.aut.android.securityapp.data.remote.ShiftApi
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

class ShiftRepositoryImpl constructor(
    private val api: ShiftApi,
    private val app: Application,
) : ShiftRepository {

    private val context = app.applicationContext

    override suspend fun getCurrentForPerson(jobId: Int, personId: Int): Resource<Shift> {
        val connection = try{
            val result = api.getCurrentForPerson(jobId = jobId, personId = personId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_queried_active_shift_for_person_in_job), data = result.body()!!)
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

    override suspend fun getById(shiftId: Int): Resource<Shift> {
        val connection = try{
            val result = api.getById(shiftId = shiftId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(
                    R.string.repository_message_successfully_queried_shift_with_id,
                    shiftId.toString()
                ), data = result.body()!!)
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

    override suspend fun createShift(shiftData: CreateShiftData): Resource<Shift> {
        val connection = try{
            val result = api.createShift(shiftData = shiftData)

            val data = if(result.isSuccessful && result.code() == 201){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_created_a_new_shift), data = result.body()!!)
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

    override suspend fun endShift(shiftId: Int): Resource<Shift> {
        val connection = try{
            val result = api.endShift(shiftId = shiftId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_ended_shift), data = result.body()!!)
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

    override suspend fun getAllPendingInJob(jobId: Int): Resource<List<Shift>> {
        val connection = try{
            val result = api.getAllPendingInJob(jobId = jobId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(
                    R.string.repository_message_successfully_queried_all_pending_shifts_in_job_with_id,
                    jobId.toString()
                ), data = result.body()!!)
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

    override suspend fun acceptShift(shiftId: Int): Resource<Unit> {
        val connection = try{
            val result = api.acceptShift(shiftId = shiftId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_accepted_shift), data = result.body()!!)
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

    override suspend fun denyShift(shiftId: Int): Resource<Unit> {
        val connection = try{
            val result = api.denyShift(shiftId = shiftId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_declined_shift), data = result.body()!!)
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

    override suspend fun getAllJudgedShifts(jobId: Int, personId: Int): Resource<List<Shift>> {
        val connection = try{
            val result = api.getAllJudgedShifts(jobId = jobId, personId = personId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_queried_all_judged_shifts), data = result.body()!!)
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

    override suspend fun updateShift(
        shiftId: Int,
        updateShiftData: UpdateShiftData
    ): Resource<Shift> {
        val connection = try{
            val result = api.updateShift(shiftId = shiftId, updateShiftData = updateShiftData)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_updated_shift), data = result.body()!!)
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

    override suspend fun getJobStatistics(jobId: Int): Resource<JobStatistic> {
        val connection = try{
            val result = api.getJobStatistics(jobId = jobId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(
                    R.string.repository_message_successfully_get_job_statistics_for_job_id,
                    jobId.toString()
                ), data = result.body()!!)
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
}