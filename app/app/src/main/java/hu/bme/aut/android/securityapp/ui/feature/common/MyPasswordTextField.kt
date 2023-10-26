package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import hu.bme.aut.android.securityapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit = {},
    label: @Composable() (() -> Unit)?,
    isError: Boolean = false,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    supportingText: @Composable() (() -> Unit)? = null,
    modifier: Modifier = Modifier,
){
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = {
            onPasswordChange(it)
        },
        label = label,
        singleLine = true,
        trailingIcon = {
            val image = if(passwordVisible){
                Icons.Filled.Visibility
            } else{
                Icons.Filled.VisibilityOff
            }

            val description = if(passwordVisible) {
                stringResource(R.string.composable_hide_password)
            } else {
                stringResource(R.string.composable_show_password)
            }

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
        supportingText = supportingText,
        modifier = modifier
    )
}