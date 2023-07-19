package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyDoublePasswordTextFields(
    password: String = "",
    onPasswordChange: (String, String) -> Unit,
    readOnly: Boolean = false,
    enabled: Boolean = true,
){
    var _password by rememberSaveable { mutableStateOf(password) }
    var _password_confirm by rememberSaveable { mutableStateOf(password) }
    var passwordError by rememberSaveable { mutableStateOf(false) }

    Column {
        MyPasswordTextField(
            password = _password,
            onPasswordChange = {
                _password = it
                passwordError = checkPasswordError(_password, _password_confirm)
                onPasswordChange(_password, _password_confirm)
            },
            isError = passwordError,
            readOnly = readOnly,
            enabled = enabled
        )

        MyPasswordTextField(
            password = _password_confirm,
            onPasswordChange = {
                _password_confirm = it
                passwordError = checkPasswordError(_password, _password_confirm)
                onPasswordChange(_password, _password_confirm)
            },
            isError = passwordError,
            readOnly = readOnly,
            enabled = enabled
        )
    }
}

private fun checkPasswordError(
    pass1: String,
    pass2: String,
): Boolean{
    if(pass1 != pass2) return true

    if(pass1.length > 30) return true

    if(pass1.isEmpty()) return true

    return false
}

@Composable
@Preview(showBackground = true)
fun MyDoublePasswordTextFieldsPreview(){
    MyDoublePasswordTextFields(
        onPasswordChange = { p1, p2 ->

        }
    )
}