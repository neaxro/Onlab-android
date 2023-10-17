package hu.bme.aut.android.securityapp.ui.feature.mainmenu.menus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.ui.feature.common.ManageShiftCard
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.shift.ShiftAction
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.shift.ShiftScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftScreen(
    viewModel: ShiftScreenViewModel = hiltViewModel()
){
    val context = LocalContext.current

    val state = viewModel.state.collectAsState().value
    val wages = viewModel.wages.collectAsState().value
    val currentWage = viewModel.wage.collectAsState().value
    val shiftState = viewModel.shiftState.collectAsState().value

    var checked by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Shift",
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.evoke(ShiftAction.RefreshData)
                        }
                    ) {
                        Icon(imageVector = Icons.Rounded.Refresh, contentDescription = "Reload")
                    }
                }
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            ManageShiftCard(
                shiftInformation = shiftState.shift,
                wages = wages,
                wage = currentWage,
                onClick = { isStart ->
                    if(isStart) {
                        viewModel.evoke(ShiftAction.StartShift)
                    }
                    else{
                        viewModel.evoke(ShiftAction.StopShift)
                    }
                },
                onWageChange = { newWage ->
                    viewModel.evoke(ShiftAction.ChangeWage(wage = newWage))
                },
                enabled = shiftState.isActive || LoggedPerson.CURRENT_JOB_ID == 0,
                buttonEnabled = LoggedPerson.CURRENT_JOB_ID > 0,
                modifier = Modifier
                    .padding(10.dp)
            )

            /*EnableGpsCard(
                checked = checked,
                checkedChanged = {
                    checked = !checked
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )*/
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreenShift(){
    ShiftScreen()
}