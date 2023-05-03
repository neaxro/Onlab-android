package hu.bme.aut.android.securityapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import hu.bme.aut.android.securityapp.ui.navigation.Screen
import hu.bme.aut.android.securityapp.ui.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel,
){
    val context = LocalContext.current

    var username by remember { viewModel.username }
    val usernameError = remember { mutableStateOf(false) }

    var password by remember { viewModel.password }
    val passwordError = remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }

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

        OutlinedTextField(
            value = username,
            onValueChange = {
                usernameError.value = false
                username = it
            },
            label = {Text(text = "Username")},
            singleLine = true,
            isError = usernameError.value
        )

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                passwordError.value = false
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = passwordError.value
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            onClick = {
                if(username.isEmpty()){
                    usernameError.value = true
                }
                else if(password.isEmpty()){
                    passwordError.value = true
                }

                viewModel.loginUser(
                    onSuccess = { numberOfJobs ->
                    // If login is successful navigate to the proper screen
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
                },
                    onError = {errorMessage ->
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                    }
                )
            },
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