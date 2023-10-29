package hu.bme.aut.android.securityapp.ui.feature.createJob

//import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.ui.feature.common.MySnackbarHost
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJobScreen(
    viewModel: CreateJobViewModel = hiltViewModel(),
    navigateToMainMenu: () -> Unit = {},
){
    val context = LocalContext.current

    val jobData = viewModel.createJobData.collectAsState().value

    LaunchedEffect(viewModel.creationState){
        viewModel.creationState.collect{ newState ->
            if (newState is CreationState.Created){
                navigateToMainMenu()
            }
        }
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.composable_create_job_title),
                onNavigate = {
                    navigateToMainMenu()
                },
                screenState = viewModel.screenState.collectAsState(),
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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            OutlinedTextField(
                value = jobData.title,
                onValueChange = { newJobName ->
                    viewModel.evoke(CreateJobAction.UpdateJobName(jobName = newJobName))
                },
                label = { Text(text = stringResource(R.string.composable_job_s_name))},
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = jobData.description,
                onValueChange = { newDescription ->
                    viewModel.evoke(CreateJobAction.UpdateDescription(description = newDescription))
                },
                label = { Text(text = stringResource(R.string.composable_description))},
                maxLines = 3,
                modifier = Modifier.height(150.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(

                onClick = {
                    viewModel.evoke(CreateJobAction.CreateJob)
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f),
            ) {
                Text(text = stringResource(R.string.composable_create))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun screenPrev(){
    CreateJobScreen(
        hiltViewModel<CreateJobViewModel>(),
    )
}