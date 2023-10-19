package hu.bme.aut.android.securityapp.ui.feature.mainmenu.jobs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.constants.Constants
import hu.bme.aut.android.securityapp.constants.DataFieldErrors
import hu.bme.aut.android.securityapp.ui.feature.common.MaxLengthOutlinedTextField
import hu.bme.aut.android.securityapp.ui.feature.common.MySnackbarHost
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar

@Composable
fun JobEditScreen(
    viewModel: JobEditViewModel = hiltViewModel(),
    navigateBack: () -> Unit
){
    val context = LocalContext.current

    val job = viewModel.updateJobData.collectAsState().value
    val errors = viewModel.errors.collectAsState().value

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Edit Job",
                onNavigate = { navigateBack() },
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

            MaxLengthOutlinedTextField(
                value = job.title,
                label = { Text("Title") },
                onValueChange = { newTitle ->
                    if(newTitle.length <= Constants.MAX_JOB_TITLE_LENGTH) {
                        viewModel.evoke(JobEditAction.UpdateTitle(title = newTitle))
                    }
                },
                isError = errors.titleError !is DataFieldErrors.NoError,
                singleLine = true,
                maxLength = Constants.MAX_JOB_TITLE_LENGTH,
                readOnly = false,
                enabled = true,
                modifier = Modifier.width(300.dp)
            )

            MaxLengthOutlinedTextField(
                value = job.description,
                label = { Text("Description") },
                onValueChange = { newDescription ->
                    if(newDescription.length <= Constants.MAX_JOB_DESCRIPTION_LENGTH) {
                        viewModel.evoke(JobEditAction.UpdateDescription(description = newDescription))
                    }
                },
                isError = errors.descriptionError !is DataFieldErrors.NoError,
                singleLine = false,
                maxLength = Constants.MAX_JOB_DESCRIPTION_LENGTH,
                readOnly = false,
                enabled = true,
                modifier = Modifier.width(300.dp)
            )

            OutlinedButton(
                onClick = { viewModel.evoke(JobEditAction.UpdateJob) },
                enabled = !errors.isError()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    Text(text = "Update")
                }
            }
        }
    }
}