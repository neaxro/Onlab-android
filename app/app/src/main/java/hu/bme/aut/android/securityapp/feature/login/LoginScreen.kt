package hu.bme.aut.android.securityapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import hu.bme.aut.android.securityapp.ui.navigation.Screen
import hu.bme.aut.android.securityapp.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel
){
    var username by remember { viewModel.username }
    var password by remember { viewModel.password }

    var passwordVisible by remember { mutableStateOf(false) }

    var loginResult by remember { viewModel.login }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(30.dp))

        TextField(
            value = username,
            onValueChange = {
                username = it
            },
            label = {Text(text = "Username")},
            singleLine = true
        )
        
        Spacer(modifier = Modifier.padding(20.dp))
        
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {Text(text = "Password")},
            singleLine = true,
            trailingIcon = {
                val image = if(passwordVisible){
                    Icons.Filled.Visibility
                } else{
                    Icons.Filled.VisibilityOff
                }

                val description = if(passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(imageVector = image, description)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.padding(20.dp))

        LoginOrRegister(navController = navController){
            viewModel.LoginUser(){numberOfJobs ->
                if(numberOfJobs > 0){
                    // Navigate to Main Screen
                    navController.navigate(Screen.MainMenu.route){
                        popUpTo(Screen.Login.route){
                            inclusive = true
                        }
                    }
                }
                else {
                    // Navigate to No Job screen
                    navController.navigate(Screen.NoJob.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }

        // TODO: Kicsit szebben megoldani a visszajelzést, esetleg egy loading effektet használni stb..
        Text(text = loginResult)
    }
}

@Composable
fun LoginOrRegister(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    loginAction: () -> Unit = {}
){
    Column(modifier = modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            onClick = { loginAction() },
        ) {
            Text(text = "Sign in")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Not a member?")

            TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
                Text(text = "Join now")
            }
        }
    }
}