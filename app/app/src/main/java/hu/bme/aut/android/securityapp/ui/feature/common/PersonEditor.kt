package hu.bme.aut.android.securityapp.ui.feature.common

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.constants.DataFieldErrors
import hu.bme.aut.android.securityapp.constants.containsType
import hu.bme.aut.android.securityapp.constants.getErrorMessage
import hu.bme.aut.android.securityapp.data.model.people.PersonDefault

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonEditor(
    person: PersonDefault = PersonDefault(),
    onPersonChange: (PersonDefault) -> Unit = {},
    onUriChange: (Uri?) -> Unit,
    onPasswordsChange: (String, String) -> Unit,
    readOnly: Boolean = true,
    enabled: Boolean,
    newPassword: Boolean = false,
    errors: Set<DataFieldErrors>,
    modifier: Modifier = Modifier,
){
    var password by rememberSaveable { mutableStateOf("") }
    var passwordConfirm by rememberSaveable { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = person.fullName,
            onValueChange = {
                onPersonChange(person.copy(fullName = it))
            },
            label = {
                Text(text = stringResource(R.string.composable_fullname))
            },
            modifier = Modifier
                .padding(bottom = 5.dp),
            readOnly = readOnly,
            enabled = enabled,
            isError = errors.containsType(errorType = DataFieldErrors.FullNameError::class.java),
            supportingText = {
                 errors.getErrorMessage(errorType = DataFieldErrors.FullNameError::class.java).let {
                     if(it.isNotEmpty()){
                         MySupportingText(text = it)
                     }
                 }
            },
        )

        OutlinedTextField(
            value = person.username,
            onValueChange = {
                onPersonChange(person.copy(username = it))
            },
            label = {
                Text(text = stringResource(R.string.composable_username))
            },
            modifier = Modifier
                .padding(bottom = 5.dp),
            readOnly = readOnly,
            enabled = enabled,
            isError = errors.containsType(errorType = DataFieldErrors.UserNameError::class.java),
            supportingText = {
                errors.getErrorMessage(errorType = DataFieldErrors.UserNameError::class.java).let {
                    if(it.isNotEmpty()){
                        MySupportingText(text = it)
                    }
                }
            },
        )

        OutlinedTextField(
            value = person.nickname,
            onValueChange = {
                onPersonChange(person.copy(nickname = it))
            },
            label = {
                Text(text = stringResource(R.string.composable_nickname))
            },
            modifier = Modifier
                .padding(bottom = 5.dp),
            readOnly = readOnly,
            enabled = enabled,
            isError = errors.containsType(errorType = DataFieldErrors.NickNameError::class.java),
            supportingText = {
                errors.getErrorMessage(errorType = DataFieldErrors.NickNameError::class.java).let {
                    if(it.isNotEmpty()){
                        MySupportingText(text = it)
                    }
                }
            },
        )

        OutlinedTextField(
            value = person.email,
            onValueChange = {
                onPersonChange(person.copy(email = it))
            },
            label = {
                Text(text = stringResource(R.string.composable_email_address))
            },
            modifier = Modifier
                .padding(bottom = 5.dp),
            readOnly = readOnly,
            enabled = enabled,
            isError = errors.containsType(errorType = DataFieldErrors.EmailAddressError::class.java),
            supportingText = {
                errors.getErrorMessage(errorType = DataFieldErrors.EmailAddressError::class.java).let {
                    if(it.isNotEmpty()){
                        MySupportingText(text = it)
                    }
                }
            },
        )

        if(newPassword){
            MyPasswordTextField(
                password = password,
                onPasswordChange = {
                    password = it
                    onPasswordsChange(password, passwordConfirm)
                },
                isError = errors.containsType(errorType = DataFieldErrors.PasswordError::class.java),
                readOnly = readOnly,
                enabled = enabled,
                label = { Text(text = stringResource(R.string.composable_password)) },
                modifier = Modifier
                    .padding(bottom = 5.dp),
                supportingText = {
                    errors.getErrorMessage(errorType = DataFieldErrors.PasswordError::class.java).let {
                        if(it.isNotEmpty()){
                            MySupportingText(text = it)
                        }
                    }
                },
            )

            MyPasswordTextField(
                password = passwordConfirm,
                onPasswordChange = {
                    passwordConfirm = it
                    onPasswordsChange(password, passwordConfirm)
                },
                isError = errors.containsType(errorType = DataFieldErrors.PasswordError::class.java)
                        || errors.containsType(errorType = DataFieldErrors.PasswordMismatchError::class.java),
                readOnly = readOnly,
                enabled = enabled,
                label = { Text(text = stringResource(R.string.composable_password_again)) },
                modifier = Modifier,
                supportingText = {
                    errors.getErrorMessage(errorType = DataFieldErrors.PasswordMismatchError::class.java).let {
                        if(it.isNotEmpty()){
                            MySupportingText(text = it)
                        }
                    }
                },
            )
        }

        PhotoPicker(
            imageSelected = { uri ->
                onUriChange(uri)
            },
            enabled = enabled,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PersonEditorPreview(){
    PersonEditor(
        person = PersonDefault("Nemes Axel Roland", "nemesa", "Axi", "neaxro@gmail.com"),
        readOnly = false,
        newPassword = true,
        onUriChange = {},
        enabled = true,
        onPasswordsChange = { a, b ->

        },
        errors = setOf()
    )
}