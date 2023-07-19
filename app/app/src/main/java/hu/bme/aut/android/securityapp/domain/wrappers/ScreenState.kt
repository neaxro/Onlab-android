package hu.bme.aut.android.securityapp.domain.wrappers

sealed class ScreenState(val message: String = "") {
    class Loading(message: String = "Loading...") : ScreenState(message)
    class Finished(message: String = "Finished!") : ScreenState(message)
    class Error(message: String) : ScreenState(message)
}