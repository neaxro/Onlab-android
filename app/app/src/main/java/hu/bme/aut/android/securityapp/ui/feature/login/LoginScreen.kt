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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.ui.feature.common.MySnackbarHost
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.viewmodel.LoginAction
import hu.bme.aut.android.securityapp.ui.viewmodel.LoginState
import hu.bme.aut.android.securityapp.ui.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onSuccessLogin: () -> Unit = {},
    navigateToRegister: () -> Unit = {},
){
    val context = LocalContext.current

    val loginData = viewModel.loginData.collectAsState().value
    val errors = viewModel.errors.collectAsState().value

    LaunchedEffect(viewModel.loginState){
        viewModel.loginState.collect{ newState ->
            if(newState is LoginState.LoggedIn){
                onSuccessLogin()
            }
        }
    }

    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.composable_login_title),
                screenState = viewModel.screenState.collectAsState()
            )
        },
        snackbarHost = {
            MySnackbarHost(
                screenState = viewModel.screenState,
            )
        },
    ) {
        val paddingTop = it.calculateTopPadding()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop),
        ) {
            OutlinedTextField(
                value = loginData.username,
                onValueChange = {
                    viewModel.evoke(LoginAction.UpdateUsername(username = it))
                },
                label = { Text(text = stringResource(id = R.string.composable_username)) },
                singleLine = true,
                isError = errors.userName
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = loginData.password,
                onValueChange = {
                    viewModel.evoke(LoginAction.UpdatePassword(password = it))
                },
                label = { Text(text = stringResource(id = R.string.composable_password)) },
                singleLine = true,
                trailingIcon = {
                    val image = if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    val description = if (passwordVisible) stringResource(id = R.string.composable_hide_password) else stringResource(id = R.string.composable_show_password)

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
                isError = errors.password
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                onClick = {
                    viewModel.evoke(LoginAction.Login)
                },
            ) {
                Text(text = stringResource(R.string.composable_sign_in))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = stringResource(R.string.composable_not_a_member))

                TextButton(
                    onClick = {
                        navigateToRegister()
                    }
                ) {
                    Text(text = stringResource(R.string.composable_join_now))
                }
            }
        }
    }
}