package hu.bme.aut.android.securityapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.ui.feature.common.MySnackbarHost
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.RegisterEditor
import hu.bme.aut.android.securityapp.ui.feature.register.RegisterAction
import hu.bme.aut.android.securityapp.ui.feature.register.RegisterViewModel
import hu.bme.aut.android.securityapp.ui.feature.register.isError

@ExperimentalMaterial3Api
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit = {},
){
    val context = LocalContext.current

    val person = viewModel.person.collectAsState().value
    val rePassword = viewModel.rePassword.collectAsState().value
    val errors = viewModel.errors.collectAsState().value

    Scaffold (
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.composable_register_title),
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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
        ) {
            RegisterEditor(
                fullName = person.fullName,
                fullNameChange = { viewModel.evoke(RegisterAction.ChangeFullName(fullName = it)) },
                fullNameError = errors.fullName,
                userName = person.username,
                userNameChange = { viewModel.evoke(RegisterAction.ChangeUserName(userName = it)) },
                userNameError = errors.userName,
                nickName = person.nickname,
                nickNameChange = { viewModel.evoke(RegisterAction.ChangeNickName(nickName = it)) },
                nickNameError = errors.nickName,
                emailAddress = person.email,
                emailAddressChange = { viewModel.evoke(RegisterAction.ChangeEmailAddress(emailAddress = it)) },
                emailAddressError = errors.emailAddress,
                password = person.password,
                passwordChange = { viewModel.evoke(RegisterAction.ChangePassword(password = it)) },
                passwordError = errors.password || errors.passwordMismatch,
                passwordConfirm = rePassword,
                passwordConfirmChange = { viewModel.evoke(RegisterAction.ChangeRePassword(rePassword = it)) },
                passwordConfirmError = errors.password || errors.passwordMismatch
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    onClick = {
                        viewModel.evoke(RegisterAction.Register)
                    },
                    enabled = !errors.isError()
                ) {
                    Text(text = stringResource(R.string.composable_register_now))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(text = stringResource(R.string.composable_already_have_account))

                    TextButton(
                        onClick = {
                            navigateToLogin()
                        }
                    ) {
                        Text(text = stringResource(R.string.composable_login_now))
                    }
                }
            }
        }
    }
}