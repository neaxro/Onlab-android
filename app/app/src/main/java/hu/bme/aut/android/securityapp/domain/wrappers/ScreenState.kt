package hu.bme.aut.android.securityapp.domain.wrappers

import android.content.Context
import hu.bme.aut.android.securityapp.MyApp
import hu.bme.aut.android.securityapp.R

sealed class ScreenState(
    val message: String = "",
    val show: Boolean,
) {
    class Loading(val context: Context = MyApp.applicationContext(), message: String = context.getString(R.string.wrappers_screen_state_loading), show: Boolean = false) : ScreenState(message, show)
    class Finished(val context: Context = MyApp.applicationContext(), message: String = context.getString(R.string.wrappers_screen_state_finished), show: Boolean = false) : ScreenState(message, show)
    class Error(val context: Context = MyApp.applicationContext(), message: String, show: Boolean = true) : ScreenState(message, show)
    class Success(val context: Context = MyApp.applicationContext(), message: String = context.getString(R.string.wrappers_screen_state_success), show: Boolean = false) : ScreenState(message, show)
}