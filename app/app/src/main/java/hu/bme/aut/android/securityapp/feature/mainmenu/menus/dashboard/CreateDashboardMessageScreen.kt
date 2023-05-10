package hu.bme.aut.android.securityapp.feature.mainmenu.menus.dashboard

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.feature.common.EditDashboardMessageScreen
import hu.bme.aut.android.securityapp.feature.common.MyTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDashboardMessageScreen(
    viewModel: CreateDashboardMessageViewModel = hiltViewModel(),
    navigateBack: () -> Unit
){
    val context = LocalContext.current
    val wages = viewModel.categories.collectAsState().value

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Create Message",
                onNavigate = navigateBack
            )
        }
    ){ paddingValues ->
        val paddingTop = paddingValues.calculateTopPadding()

        EditDashboardMessageScreen(
            wages = wages,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop),
            onClick = { newMessage ->
                viewModel.createMessage(
                    message = newMessage,
                    onSuccess = {
                        navigateBack()
                    },
                    onError = { errorMessage ->
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                )
            },
            buttonContent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = "Create Message"
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "Create")
                }
            }
        )
    }
}

@Composable
@Preview
fun CreateDashboardMessgaeScreenPreview(){
    CreateDashboardMessageScreen(
        navigateBack = {}
    )
}