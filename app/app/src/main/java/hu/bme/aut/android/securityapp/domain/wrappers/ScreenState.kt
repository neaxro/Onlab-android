package hu.bme.aut.android.securityapp.domain.wrappers

sealed class ScreenState(val message: String = "", val show: Boolean) {
    class Loading(message: String = "Loading...", show: Boolean = false) : ScreenState(message, show)
    class Finished(message: String = "Finished!", show: Boolean = false) : ScreenState(message, show)
    class Error(message: String, show: Boolean = true) : ScreenState(message, show)
    class Success(message: String = "Success!", show: Boolean = false) : ScreenState(message, show)
}