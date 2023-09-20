package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.pendingshifts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingShiftsScreen(
    navigateBack: () -> Unit,
){
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Pending Shifts",
                onNavigate = {
                    navigateBack()
                }
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
        ) {
            Text(text = "Pending Shifts")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PendingShiftsScreenPreview(){
    PendingShiftsScreen(
        navigateBack = {}
    )
}