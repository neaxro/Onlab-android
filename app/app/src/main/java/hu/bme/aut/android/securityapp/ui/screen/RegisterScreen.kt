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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.securityapp.data.model.LoginData
import hu.bme.aut.android.securityapp.ui.navigation.Screen

@Composable
fun RegisterScreen(
    navController: NavHostController
){
    RegisterScr(navController = navController)
    
    /*Box(
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
    }*/
}

fun matchPasswords(pass1: String, pass2: String): Boolean{
    return pass1 == pass2
}

@Composable
fun RegisterScr(
    navController: NavHostController
){
    var username by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var password1 by remember { mutableStateOf("") }
    var passwordVisible1 by remember { mutableStateOf(false) }
    var password2 by remember { mutableStateOf("") }
    var passwordVisible2 by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

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
            value = emailAddress,
            onValueChange = {
                emailAddress = it
            },
            label = {
                Text(text = "Email address")
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        TextField(
            value = password1,
            onValueChange = {
                isError = matchPasswords(password1, password2)
                password1 = it
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
            isError = isError,
        )

        TextField(
            value = password2,
            onValueChange = {
                isError = matchPasswords(password1, password2)
                password2 = it
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
            isError = isError
        )

        Spacer(modifier = Modifier.padding(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                if(!isError){
                    // TODO REGISTER
                }
            }) {
                Text(text = "Register")
            }

            Button(onClick = {
                navController.navigate(Screen.Login.route)
            }) {
                Text(text = "Login")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun prew(){
    RegisterScr(navController = rememberNavController())
}