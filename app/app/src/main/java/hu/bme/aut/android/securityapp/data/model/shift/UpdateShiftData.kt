package hu.bme.aut.android.securityapp.data.model.shift

import hu.bme.aut.android.securityapp.constants.Constants

data class UpdateShiftData(
    val startTime: String = "",
    val endTime: String = "",
    val statusId: Int = Constants.SHIFT_PENDING_STATUS_ID,
    val wageId: Int = 0,
)