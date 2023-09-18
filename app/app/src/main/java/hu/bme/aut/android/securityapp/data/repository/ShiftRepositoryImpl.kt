package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.data.model.shift.CreateShiftData
import hu.bme.aut.android.securityapp.data.model.shift.Shift
import hu.bme.aut.android.securityapp.data.remote.ShiftApi
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

class ShiftRepositoryImpl constructor(
    private val api: ShiftApi,
    private val app: Application,
) : ShiftRepository {
    override suspend fun getCurrentForPerson(jobId: Int, personId: Int): Resource<Shift> {
        val connection = try{
            val result = api.getCurrentForPerson(jobId = jobId, personId = personId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Succesfully queried active shift for person in job!", data = result.body()!!)
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

    override suspend fun getById(shiftId: Int): Resource<Shift> {
        val connection = try{
            val result = api.getById(shiftId = shiftId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Succesfully queried shift by id!", data = result.body()!!)
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

    override suspend fun createShift(shiftData: CreateShiftData): Resource<Shift> {
        val connection = try{
            val result = api.createShift(shiftData = shiftData)

            val data = if(result.isSuccessful && result.code() == 201){
                Resource.Success(message = "Succesfully created a new Shift!", data = result.body()!!)
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

    override suspend fun endShift(shiftId: Int): Resource<Shift> {
        val connection = try{
            val result = api.endShift(shiftId = shiftId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = "Succesfully ended shift!", data = result.body()!!)
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
}