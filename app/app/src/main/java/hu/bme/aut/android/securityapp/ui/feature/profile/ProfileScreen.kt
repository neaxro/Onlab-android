package hu.bme.aut.android.securityapp.ui.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.PersonEditor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateBack: () -> Unit = {},
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    onLogOut: () -> Unit = {},
){
    var isReadOnly by rememberSaveable { mutableStateOf(true) }
    val person = viewModel.userData.collectAsState()

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
                        viewModel.logOut(){
                            onLogOut()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = "Log out")
                    }
                }
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = paddingTop)
                .fillMaxSize()
        ){
            PersonEditor(
                person.value,
                onPersonChange = {
                     viewModel.changeUserData(it)
                },
                newPassword = false,
                readOnly = isReadOnly,
            )

            if(!isReadOnly){
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedButton(
                    onClick = {
                        viewModel.updatePerson()
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