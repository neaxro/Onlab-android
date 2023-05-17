package hu.bme.aut.android.securityapp.ui.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateBack: () -> Unit = {},
){
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "My Profile",
                onNavigate = {
                    navigateBack()
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit profile")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = "Log out")
                    }
                }
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()

        Box(
            modifier = Modifier
                .padding(top = paddingTop)
                .fillMaxSize()
        ){
            Text(text = "Details", modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview(){
    ProfileScreen()
}