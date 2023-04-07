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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import hu.bme.aut.android.securityapp.feature.register.RegisterViewModel
import hu.bme.aut.android.securityapp.feature.ui.navigation.Screen

private fun matchPasswords(pass1: String, pass2: String): Boolean = pass1 == pass2

private fun anyFieldEmpty(vararg fields: String): Boolean{
    return fields.any {
        it.isEmpty()
    }
}

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
){
    var fullName by remember {viewModel.fullName}
    var username by remember {viewModel.username}
    var nickname by remember {viewModel.nickname}
    var email by remember {viewModel.email}
    var password by remember {viewModel.password}
    var passwordAgain by remember {viewModel.passwordAgain}

    var passwordVisible1 by remember { mutableStateOf(false) }
    var passwordVisible2 by remember { mutableStateOf(false) }

    var passwordsMatch by remember { mutableStateOf(true)}

    var registerState by remember {
        viewModel.registrationState
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "Register",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = fullName,
            onValueChange = {
                fullName = it
            },
            label = {
                Text(text = "Full name")
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp)
        )

        TextField(
            value = username,
            onValueChange = {
                username = it
            },
            label = {
                Text(text = "Username")
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp)
        )

        TextField(
            value = nickname,
            onValueChange = {
                nickname = it
            },
            label = {
                Text(text = "Nickname")
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp)
        )

        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = "Email address")
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        TextField(
            value = password,
            onValueChange = {
                password = it
                passwordsMatch = matchPasswords(password, passwordAgain)
                registerState = if(!passwordsMatch) "Passwords do not match!" else ""
            },
            label = {Text(text = "Password")},
            singleLine = true,
            trailingIcon = {
                val image = if(passwordVisible1){
                    Icons.Filled.Visibility
                } else{
                    Icons.Filled.VisibilityOff
                }

                val description = if(passwordVisible1) "Hide password" else "Show password"

                IconButton(onClick = {
                    passwordVisible1 = !passwordVisible1
                }) {
                    Icon(imageVector = image, description)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible1) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.padding(5.dp),
        )

        TextField(
            value = passwordAgain,
            onValueChange = {
                passwordAgain = it
                passwordsMatch = matchPasswords(password, passwordAgain)
                registerState = if(!passwordsMatch) "Passwords do not match!" else ""
            },
            label = {Text(text = "Verify password")},
            singleLine = true,
            trailingIcon = {
                val image = if(passwordVisible2){
                    Icons.Filled.Visibility
                } else{
                    Icons.Filled.VisibilityOff
                }

                val description = if(passwordVisible2) "Hide password" else "Show password"

                IconButton(onClick = {
                    passwordVisible2 = !passwordVisible2
                }) {
                    Icon(imageVector = image, description)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible2) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.padding(5.dp),
        )

        Spacer(modifier = Modifier.padding(20.dp))

        RegisterOrLogin(navController = navController){

            if(passwordsMatch && !anyFieldEmpty(fullName, username, nickname, email, password, passwordAgain)){
                viewModel.register()
            }
        }

        // TODO: Kicsit szebben megoldani a visszajelzést, esetleg egy loading effektet használni stb..
        Text(text = registerState)
    }
}

@Composable
fun RegisterOrLogin(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    registerAction: () -> Unit = {}
){
    Column(modifier = modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            onClick = { registerAction() },
        ) {
            Text(text = "Register Now")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Already have account?")
            
            TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
                Text(text = "Login now")
            }
        }
    }
}