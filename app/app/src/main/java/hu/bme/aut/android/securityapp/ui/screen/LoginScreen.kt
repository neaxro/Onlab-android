package hu.bme.aut.android.securityapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import hu.bme.aut.android.securityapp.ui.navigation.Screen

@Composable
fun LoginScreen(
    navController: NavHostController
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "LOGIN SCREEN")
            Button(onClick = {
                navController.navigate(Screen.Register.route)
            }) {
                Text(text = "Got to Register")
            }
        }
    }
}