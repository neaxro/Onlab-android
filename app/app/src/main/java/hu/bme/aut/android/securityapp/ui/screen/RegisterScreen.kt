package hu.bme.aut.android.securityapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import hu.bme.aut.android.securityapp.ui.navigation.Screen

@Composable
fun RegisterScreen(
    navController: NavHostController
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "REGISTER SCREEN")
            Button(onClick = {
                navController.navigate(Screen.Login.route)
            }) {
                Text(text = "Got to Login")
            }
        }
    }
}