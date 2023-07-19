package hu.bme.aut.android.securityapp.ui.feature.common

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPasswordTextField(
    password: String = "",
    onPasswordChange: (String) -> Unit = {},
    isError: Boolean = false,
    readOnly: Boolean = false,
    enabled: Boolean = true,
){
    var _password by remember { mutableStateOf(password) }
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = _password,
        onValueChange = {
            _password = it
            onPasswordChange(it)
        },
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        isError = isError,
        readOnly = readOnly,
        enabled = enabled,
    )
}