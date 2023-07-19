package hu.bme.aut.android.securityapp.data.repository

import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.data.model.wage.WageCreate
import hu.bme.aut.android.securityapp.data.model.wage.WageUpdate
import hu.bme.aut.android.securityapp.domain.wrappers.Resource
import okhttp3.ResponseBody

interface WageRepository {
    suspend fun getCategories(jobId: Int): Resource<List<Wage>>

    suspend fun getWages(jobId: Int): Resource<List<Wage>>

    suspend fun getWage(wageId: Int): Resource<Wage>

    suspend fun updateWage(wageId: Int, wageData: WageUpdate): Resource<Wage>

    suspend fun deleteWage(wageId: Int): Resource<ResponseBody>

    suspend fun createWage(wageData: WageCreate): Resource<Wage>
}