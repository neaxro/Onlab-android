package hu.bme.aut.android.securityapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.securityapp.data.model.people.LoginData
import hu.bme.aut.android.securityapp.ui.navigation.Screen
import hu.bme.aut.android.securityapp.ui.screen.MyAppNavHost
import hu.bme.aut.android.securityapp.ui.theme.SecurityAppTheme
import hu.bme.aut.android.securityapp.ui.viewmodel.LoginViewModel

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var startDest = Screen.MainMenu.fullRoute

        val loginViewModel: LoginViewModel by viewModels<LoginViewModel>()
        val loginData = getLoginData()

        if(loginData == null){
            startDest = Screen.Login.fullRoute
        }
        else{
            loginViewModel.username.value = loginData.username
            loginViewModel.password.value = loginData.password

            loginViewModel.loginUser(
                onSuccess = {},
                onError = { startDest = Screen.Login.fullRoute }
            )
        }

        setContent {
            SecurityAppTheme {
                MyAppNavHost(startDestination = startDest)
            }
        }
    }

    private fun getLoginData(): LoginData? {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return null
        val username = sharedPref.getString("USERNAME", null)
        val password = sharedPref.getString("PASSWORD", null)

        if(username == null || password == null) return null

        return LoginData(username, password)
    }
}
