package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterEditor(
    fullName: String,
    fullNameChange: (String) -> Unit,
    fullNameError: Boolean,
    userName: String,
    userNameChange: (String) -> Unit,
    userNameError: Boolean,
    nickName: String,
    nickNameChange: (String) -> Unit,
    nickNameError: Boolean,
    emailAddress: String,
    emailAddressChange: (String) -> Unit,
    emailAddressError: Boolean,
    password: String,
    passwordChange: (String) -> Unit,
    passwordError: Boolean,
    rePassword: String,
    rePasswordChange: (String) -> Unit,
    rePasswordError: Boolean,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var rePasswordVisible by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullNameChange(it) },
            label = {
                Text(text = "Full name")
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp),
            isError = fullNameError
        )

        OutlinedTextField(
            value = userName,
            onValueChange = { userNameChange(it) },
            label = {
                Text(text = "Username")
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp),
            isError = userNameError
        )

        OutlinedTextField(
            value = nickName,
            onValueChange = { nickNameChange(it) },
            label = {
                Text(text = "Nickname")
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp),
            isError = nickNameError
        )

        OutlinedTextField(
            value = emailAddress,
            onValueChange = { emailAddressChange(it) },
            label = {
                Text(text = "Email address")
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = emailAddressError
        )

        OutlinedTextField(
            value = password,
            onValueChange = { passwordChange(it) },
            label = { Text(text = "Password") },
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
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.padding(5.dp),
            isError = passwordError
        )

        OutlinedTextField(
            value = rePassword,
            onValueChange = { rePasswordChange(it) },
            label = { Text(text = "Verify password") },
            singleLine = true,
            trailingIcon = {
                val image = if(rePasswordVisible){
                    Icons.Filled.Visibility
                } else{
                    Icons.Filled.VisibilityOff
                }

                val description = if(rePasswordVisible) "Hide password" else "Show password"

                IconButton(onClick = {
                    rePasswordVisible = !rePasswordVisible
                }) {
                    Icon(imageVector = image, description)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (rePasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.padding(5.dp),
            isError = rePasswordError
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RegisterEditorPreview(){
    RegisterEditor(
        fullName = "",
        fullNameChange = {},
        fullNameError = false,
        userName = "",
        userNameChange = {},
        userNameError = false,
        nickName = "",
        nickNameChange = {},
        nickNameError = false,
        emailAddress = "",
        emailAddressChange = {},
        emailAddressError = false,
        password = "",
        passwordChange = {},
        passwordError = false,
        rePassword = "",
        rePasswordChange = {},
        rePasswordError = false
    )
}