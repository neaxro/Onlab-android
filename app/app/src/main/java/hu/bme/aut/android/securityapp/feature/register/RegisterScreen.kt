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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.securityapp.data.model.LoginData
import hu.bme.aut.android.securityapp.data.model.RegisterData
import hu.bme.aut.android.securityapp.feature.register.RegisterViewModel
import hu.bme.aut.android.securityapp.ui.navigation.Screen
import hu.bme.aut.android.securityapp.ui.viewmodel.LoginViewModel

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel
){
    RegisterScr(navController = navController, viewModel = viewModel)
}
@Composable
fun RegisterScr(
    navController: NavHostController,
    viewModel: RegisterViewModel
){
    var passwordVisible1 by remember { mutableStateOf(false) }
    var passwordVisible2 by remember { mutableStateOf(false) }

    var success by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Register",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.padding(20.dp))

        TextField(
            value = viewModel.fullName.value,
            onValueChange = {
                viewModel.changeFullName(it)
            },
            label = {
                Text(text = "Full name")
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp)
        )

        TextField(
            value = viewModel.username.value,
            onValueChange = {
                viewModel.changeUsername(it)
            },
            label = {
                Text(text = "Username")
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp)
        )

        TextField(
            value = viewModel.nickname.value,
            onValueChange = {
                viewModel.changeNickname(it)
            },
            label = {
                Text(text = "Nickname")
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp)
        )

        TextField(
            value = viewModel.email.value,
            onValueChange = {
                viewModel.changeEmail(it)
            },
            label = {
                Text(text = "Email address")
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        TextField(
            value = viewModel.password.value,
            onValueChange = {
                viewModel.changePassword(it)
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
            value = viewModel.passwordAgain.value,
            onValueChange = {
                viewModel.changePasswordAgain(it)
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                viewModel.register({success = true}, {success = false})
            }) {
                Text(text = "Register")
            }

            Button(onClick = {
                navController.navigate(Screen.Login.route)
            }) {
                Text(text = "Login")
            }
        }

        if(success){
            Text(text = "Successfull Registration!")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun prew(){
    RegisterScr(navController = rememberNavController(), viewModel = viewModel())
}