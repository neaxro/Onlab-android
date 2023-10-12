package hu.bme.aut.android.securityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.securityapp.ui.navigation.Screen
import hu.bme.aut.android.securityapp.ui.screen.MyAppNavHost
import hu.bme.aut.android.securityapp.ui.theme.SecurityAppTheme

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startDest = Screen.Login.fullRoute

        setContent {
            SecurityAppTheme {
                MyAppNavHost(startDestination = startDest)
            }
        }
    }
}
