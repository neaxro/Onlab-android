package hu.bme.aut.android.securityapp.feature.createJob

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun CreateJobScreen(
    viewModel: CreateJobViewModel,
    navController: NavController
){
    val context = LocalContext.current

    var jobname by remember { viewModel.jobname }
    var description by remember { viewModel.description }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = jobname,
            onValueChange = {
                jobname = it
            },
            label = { Text(text = "Name of the job")},
            singleLine = true
        )

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
            },
            label = { Text(text = "Description")},
            maxLines = 3,
            modifier = Modifier.height(150.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(

            onClick = {
                viewModel.createJob(
                onSuccess = {
                        // TODO: navigate to the main menu
                        Toast.makeText(context, "Successfully created!", Toast.LENGTH_LONG).show()
                },
                onError = {errorMessage ->
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                })
            },
            modifier = Modifier
                .fillMaxWidth(0.6f),
        ) {
            Text(text = "Create")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun screenPrev(){
    CreateJobScreen(
        hiltViewModel(),
        rememberNavController()
    )
}

private fun validateName(name: String): String{
    if(name.isEmpty()) return "Name must not be empty!"
    if(name.length > 30) return "Name is too long!"

    return ""
}

private fun validateDesc(desc: String): String{
    if(desc.length > 150) return "Description is too long!"

    return ""
}