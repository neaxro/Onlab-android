package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
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

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "My Profile",
                onNavigate = {
                    navigateBack()
                },
                actions = {
                    IconButton(onClick = {
                        isReadOnly = !isReadOnly
                    }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit profile")
                    }
                    IconButton(onClick = {
                        viewModel.evoke(ProfileAction.LogOut)
                        onLogOut()
                    }) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = "Log out")
                    }
                },
                screenState = viewModel.screenState.collectAsState()
            )
        }
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
                    contentDescription = "Profile Picture",
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
                newPassword = false,
                readOnly = isReadOnly,
                onUriChange = { uri ->
                    if(uri != null){
                        viewModel.evoke(ProfileAction.SetUri(uri = uri))
                    }
                }
            )

            if(!isReadOnly){
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedButton(
                    onClick = {
                        viewModel.evoke(ProfileAction.UpdatePerson(context = context))
                    }
                ) {
                    Row {
                        Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
                        Text(text = "Save", modifier = Modifier.padding(start = 10.dp))
                    }
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