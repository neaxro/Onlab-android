package hu.bme.aut.android.securityapp.ui.screen

import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.RegisterEditor
import hu.bme.aut.android.securityapp.ui.feature.register.RegisterEvent
import hu.bme.aut.android.securityapp.ui.feature.register.RegisterViewModel
import hu.bme.aut.android.securityapp.ui.feature.register.isError

@ExperimentalMaterial3Api
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit = {},
){
    val context = LocalContext.current

    //val screenState = viewModel.screenState.collectAsState().value
    val person = viewModel.person.collectAsState().value
    val rePassword = viewModel.rePassword.collectAsState().value
    val errors = viewModel.errors.collectAsState().value

    Scaffold (
        topBar = {
            MyTopAppBar(
                title = "Register",
                screenState = ScreenState.Finished()
            )
        }
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
                fullNameChange = { viewModel.onEvoke(RegisterEvent.ChangeFullName(fullName = it)) },
                fullNameError = errors.fullName,
                userName = person.username,
                userNameChange = { viewModel.onEvoke(RegisterEvent.ChangeUserName(userName = it)) },
                userNameError = errors.userName,
                nickName = person.nickname,
                nickNameChange = { viewModel.onEvoke(RegisterEvent.ChangeNickName(nickName = it)) },
                nickNameError = errors.nickName,
                emailAddress = person.email,
                emailAddressChange = { viewModel.onEvoke(RegisterEvent.ChangeEmailAddress(emailAddress = it)) },
                emailAddressError = errors.emailAddress,
                password = person.password,
                passwordChange = { viewModel.onEvoke(RegisterEvent.ChangePassword(password = it)) },
                passwordError = errors.password || errors.passwordMissmach,
                rePassword = rePassword,
                rePasswordChange = { viewModel.onEvoke(RegisterEvent.ChangeRePassword(rePassword = it)) },
                rePasswordError = errors.password || errors.passwordMissmach
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
                        viewModel.register(
                            onSuccess = {
                                Toast.makeText(context, "Successful!", Toast.LENGTH_LONG).show()
                            },
                            onError = { errorMessage ->
                                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    enabled = !errors.isError()
                ) {
                    Text(text = "Register Now")
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(text = "Already have account?")

                    TextButton(
                        onClick = {
                            navigateToLogin()
                        }
                    ) {
                        Text(text = "Login now")
                    }
                }
            }
        }
    }
}