package hu.bme.aut.android.securityapp.feature.createJob

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CreateJobScreen(
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TextField(
            value = "FOO 2023",
            onValueChange = {},
            label = { Text(text = "Name of the job")},
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = "Fishing On Orfű 2023",
            onValueChange = {},
            label = { Text(text = "Description")},
            maxLines = 3,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth(0.6f),
        ) {
            Text(text = "Create")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun screenPrev(){
    CreateJobScreen(rememberNavController())
}