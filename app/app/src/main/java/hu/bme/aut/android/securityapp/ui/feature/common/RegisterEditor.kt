package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.R

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
    passwordConfirm: String,
    passwordConfirmChange: (String) -> Unit,
    passwordConfirmError: Boolean,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullNameChange(it) },
            label = {
                Text(text = stringResource(R.string.composable_full_name))
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp),
            isError = fullNameError
        )

        OutlinedTextField(
            value = userName,
            onValueChange = { userNameChange(it) },
            label = {
                Text(text = stringResource(id = R.string.composable_username))
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp),
            isError = userNameError
        )

        OutlinedTextField(
            value = nickName,
            onValueChange = { nickNameChange(it) },
            label = {
                Text(text = stringResource(id = R.string.composable_nickname))
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp),
            isError = nickNameError
        )

        OutlinedTextField(
            value = emailAddress,
            onValueChange = { emailAddressChange(it) },
            label = {
                Text(text = stringResource(id = R.string.composable_email_address))
            },
            singleLine = true,
            modifier = Modifier.padding(5.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = emailAddressError
        )

        MyPasswordTextField(
            password = password,
            onPasswordChange = {
                passwordChange(it)
            },
            isError = passwordError,
            label = { Text(text = stringResource(id = R.string.composable_password)) },
            modifier = Modifier
                .padding(bottom = 5.dp),
        )

        MyPasswordTextField(
            password = passwordConfirm,
            onPasswordChange = {
                passwordConfirmChange(it)
            },
            isError = passwordConfirmError,
            label = { Text(text = stringResource(id = R.string.composable_password_again)) },
            modifier = Modifier,
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
        passwordConfirm = "",
        passwordConfirmChange = {},
        passwordConfirmError = false
    )
}