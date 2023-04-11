package hu.bme.aut.android.securityapp.feature.nojob

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.securityapp.ui.navigation.Screen

@Composable
fun NoJobScreen(
    navController: NavController
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Currently you do not have any connection with any Job!",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row {
            Button(
                onClick = {
                // TODO: Navigate
                navController.navigate(Screen.CreateJob.route)
                }
            ) {
                Text(text = "Create One")
            }

            Spacer(
                modifier = Modifier
                    .width(20.dp)
            )

            Button(onClick = {
                // TODO: Navigate
                navController.navigate(Screen.Login.route)
            }) {
                Text(text = "Join One")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun screenPrev(){
    NoJobScreen(rememberNavController())
}