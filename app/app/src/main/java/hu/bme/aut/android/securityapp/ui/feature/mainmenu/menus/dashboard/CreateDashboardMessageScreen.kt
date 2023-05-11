package hu.bme.aut.android.securityapp.ui.feature.mainmenu.menus.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.ui.feature.common.DashboardEditor
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.model.DashboardUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDashboardMessageScreen(
    viewModel: CreateDashboardMessageViewModel = hiltViewModel(),
    navigateBack: () -> Unit
){
    val state = viewModel.state.collectAsState().value
    val wages = viewModel.wages.collectAsState().value

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Create Message",
                onNavigate = navigateBack
            )
        }
    ){ paddingValues ->
        val paddingTop = paddingValues.calculateTopPadding()
        val message = state.message ?: DashboardUi()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
        ) {
            Text(
                text = message.title,
                fontSize = 22.sp,
                modifier = Modifier
            )

            DashboardEditor(
                title = message.title,
                onTitleChange = {viewModel.onEvoke(CreateDashboardEvent.ChangeTitle(it))},
                message = message.message,
                onMessageChange = {viewModel.onEvoke(CreateDashboardEvent.ChangeMessageBody(it))},
                selectedWage = message.wage,
                onWageChange = {viewModel.onEvoke(CreateDashboardEvent.ChangeWage(it))},
                wages = wages,
                onErrorChanged = {}
            )

            OutlinedButton(onClick = {
                viewModel.onEvoke(CreateDashboardEvent.SaveDashboard)
                navigateBack()
            }) {
                Row {
                    Image(imageVector = Icons.Default.Save, contentDescription = "Save dashboard")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Save")
                }
            }
        }
    }
}

@Composable
@Preview
fun CreateDashboardMessgaeScreenPreview(){
    CreateDashboardMessageScreen(
        navigateBack = {}
    )
}