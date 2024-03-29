package hu.bme.aut.android.securityapp.ui.feature.mainmenu.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.ui.feature.common.DashboardEditor
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.model.DashboardUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardDetailScreen(
    viewModel: DashboardDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
){
    var isEditing by remember { mutableStateOf(false) }

    val screenState = viewModel.screenState.collectAsState().value
    val wages = viewModel.wages.collectAsState().value

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.composable_message_detail_title),
                onNavigate = navigateBack,
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvoke(DashboardDetailEvent.DeleteDashboard)
                        navigateBack()
                    }) {
                        Image(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.composable_delete_message)
                        )
                    }
                    IconButton(onClick = {
                        if(isEditing){
                            viewModel.onEvoke(DashboardDetailEvent.StopEditingDashboard)
                        }
                        else{
                            viewModel.onEvoke(DashboardDetailEvent.EditingDashboard)
                        }
                        isEditing = !isEditing
                    }) {
                        Image(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.composable_edit_message)
                        )
                    }
                },
            )
        }
    ) { paddingValue ->
        val paddingTop = paddingValue.calculateTopPadding()
        val message = screenState.message ?: DashboardUi()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
                .verticalScroll(scrollState)

        ) {
            Text(
                text = message.title,
                fontSize = 22.sp,
                modifier = Modifier
            )
            
            DashboardEditor(
                title = message.title,
                onTitleChange = { viewModel.onEvoke(DashboardDetailEvent.ChangeTitle(it)) },
                message = message.message,
                onMessageChange = {viewModel.onEvoke(DashboardDetailEvent.ChangeMessageBody(it))},
                selectedWage = message.wage,
                onWageChange = {viewModel.onEvoke(DashboardDetailEvent.ChangeWage(it))},
                wages = wages,
                isReadOnly = !isEditing,
                onErrorChanged = {}
            )

            if(isEditing){
                OutlinedButton(onClick = {
                    viewModel.onEvoke(DashboardDetailEvent.UpdateDashboard)
                }) {
                    Row {
                        Image(
                            imageVector = Icons.Default.Save,
                            contentDescription = stringResource(R.string.composable_update_message)
                        )
                        Spacer(modifier = Modifier.width(10.dp))

                        Text(text = stringResource(R.string.composable_update))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DashboardDetailScreenPreview(){
    DashboardDetailScreen(
        navigateBack = {},
    )
}