package hu.bme.aut.android.securityapp.constants

object Constants {
    const val SERVER_ADDRESS = "http://10.0.2.2:5002/"
    const val SERVER_ADDRESS_NOT_EMULATOR = "http://192.168.1.69:5002/"

    const val SHIFT_PENDING_STATUS_ID = 1
    const val SHIFT_IN_PROGRESS_STATUS_ID = 2
    const val SHIFT_ACCEPTED_STATUS_ID = 3
    const val SHIFT_DENIED_STATUS_ID = 4

    const val MAX_JOB_TITLE_LENGTH = 30
    const val MAX_JOB_DESCRIPTION_LENGTH = 150

    const val MIN_USER_PASSWORD_LENGTH = 8
    const val MAX_USER_PASSWORD_LENGTH = 30
    const val MAX_USER_FULLNAME_LENGTH = 30
}