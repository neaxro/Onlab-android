package hu.bme.aut.android.securityapp.feature.mainmenu.menus.dashboard

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.feature.common.EditDashboardMessageScreen

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
            Surface(
                shadowElevation = 10.dp,
                tonalElevation = 5.dp
            ) {
                TopAppBar(
                    title = {
                        Text(text = "Create Message")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigateBack()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "Go Back"
                            )
                        }
                    }
                )
            }
        }
    ){ paddingValues ->
        val paddingTop = paddingValues.calculateTopPadding()

        EditDashboardMessageScreen(
            wages = wages,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop),
            onUpload = { newMessage ->
                viewModel.createMessage(
                    message = newMessage,
                    onSuccess = {
                        navigateBack()
                    },
                    onError = { errorMessage ->
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                )
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