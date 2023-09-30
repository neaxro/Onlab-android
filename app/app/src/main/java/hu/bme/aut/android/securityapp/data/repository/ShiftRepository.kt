package hu.bme.aut.android.securityapp.data.repository

import hu.bme.aut.android.securityapp.data.model.shift.CreateShiftData
import hu.bme.aut.android.securityapp.data.model.shift.Shift
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface ShiftRepository {
    suspend fun getCurrentForPerson(jobId: Int, personId: Int): Resource<Shift>

    suspend fun getById(shiftId: Int): Resource<Shift>

    suspend fun createShift(shiftData: CreateShiftData): Resource<Shift>

    suspend fun endShift(shiftId: Int): Resource<Shift>

    suspend fun getAllPendingInJob(jobId: Int): Resource<List<Shift>>

    suspend fun acceptShift(shiftId: Int): Resource<Unit>

    suspend fun denyShift(shiftId: Int): Resource<Unit>

    suspend fun getAllJudgedShifts(jobId: Int, personId: Int): Resource<List<Shift>>
}