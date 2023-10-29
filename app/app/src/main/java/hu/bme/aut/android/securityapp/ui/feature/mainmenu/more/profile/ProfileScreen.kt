package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.ui.feature.common.MySnackbarHost
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.PersonEditor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateBack: () -> Unit = {},
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    onLogOut: () -> Unit = {},
){
    val context = LocalContext.current
    var isReadOnly by rememberSaveable { mutableStateOf(true) }
    val scrollState = rememberScrollState()

    val person = viewModel.userData.collectAsState()
    val profilePictureUri = viewModel.imageUri.collectAsState().value
    val errors = viewModel.dataFieldErrors.collectAsState().value

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.composable_my_profile_title),
                onNavigate = {
                    navigateBack()
                },
                actions = {
                    IconButton(onClick = {
                        isReadOnly = !isReadOnly
                    }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(R.string.composable_edit_profile))
                    }
                    IconButton(onClick = {
                        viewModel.evoke(ProfileAction.LogOut)
                        onLogOut()
                    }) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = stringResource(
                            R.string.composable_log_out
                        ))
                    }
                },
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
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(top = paddingTop + 15.dp)
                .fillMaxSize()
                .verticalScroll(
                    state = scrollState
                )
        ){
            if(profilePictureUri != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(profilePictureUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(id = R.string.composable_profile_picture),
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 15.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            PersonEditor(
                person.value,
                onPersonChange = { newUserData ->
                    viewModel.evoke(ProfileAction.SetUserData(userData = newUserData))
                },
                readOnly = isReadOnly,
                onUriChange = { uri ->
                    if(uri != null){
                        viewModel.evoke(ProfileAction.SetUri(uri = uri))
                    }
                },
                newPassword = true,
                onPasswordsChange = { password, passwordChange ->
                    viewModel.evoke(ProfileAction.PasswordsChange(password = password, passwordChange = passwordChange))
                },
                enabled = !isReadOnly,
                errors = errors
            )

            if(!isReadOnly){
                Spacer(modifier = Modifier.padding(top = 20.dp))
                OutlinedButton(
                    onClick = {
                        viewModel.evoke(ProfileAction.UpdatePerson(context = context))
                    }
                ) {
                    Row {
                        Icon(imageVector = Icons.Default.Save, contentDescription = stringResource(
                            id = R.string.composable_save
                        ))
                        Text(text = stringResource(
                            id = R.string.composable_save
                        ), modifier = Modifier.padding(start = 10.dp))
                    }
                }

                if(profilePictureUri != null) {
                    Spacer(modifier = Modifier.padding(bottom = 50.dp))
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview(){
    ProfileScreen()
}