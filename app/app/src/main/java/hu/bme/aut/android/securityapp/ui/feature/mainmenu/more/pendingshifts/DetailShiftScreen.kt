package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.pendingshifts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.PendingShiftDetailCard
import hu.bme.aut.android.securityapp.ui.feature.common.ShiftDetailAcceptAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailShiftScreen(
    navigateBack: () -> Unit,
    viewModel: DetailShiftViewModel = hiltViewModel()
){
    val shift = viewModel.shift.collectAsState().value
    val alertDialogState = viewModel.alertDialogState.collectAsState().value

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Detailed Shift",
                onNavigate = {
                    navigateBack()
                }
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()

        Box(
            modifier = Modifier.padding(top = paddingTop)
        ) {
            PendingShiftDetailCard(
                acceptShift = {
                    viewModel.evoke(ShiftDetailAction.AcceptIntent)
                },
                denyShift = {
                    viewModel.evoke(ShiftDetailAction.DenyIntent)
                },
                shift = shift,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(10.dp)
            )

            ShiftDetailAcceptAlertDialog(
                state = alertDialogState,
                accept = { viewModel.evoke(ShiftDetailAction.Accept) },
                deny = { viewModel.evoke(ShiftDetailAction.Deny) },
                dismiss = { viewModel.evoke(ShiftDetailAction.Dismiss) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailShiftScreenPreview(){
    DetailShiftScreen(
        navigateBack = {}
    )
}