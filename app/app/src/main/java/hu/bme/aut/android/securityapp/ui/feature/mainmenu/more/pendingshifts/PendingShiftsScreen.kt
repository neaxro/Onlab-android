package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.pendingshifts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.ui.feature.common.MySnackbarHost
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.ShiftCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingShiftsScreen(
    navigateBack: () -> Unit,
    navigateToDetailShift: (Int) -> Unit,
    viewModel: PendingShiftsViewModel = hiltViewModel()
){
    val shifts = viewModel.shifts.collectAsState().value
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Pending Shifts",
                onNavigate = {
                    navigateBack()
                },
                actions = {
                    IconButton(onClick = { viewModel.evoke(PendingShiftsAction.Refresh) }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                },
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

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
        ){
            items(shifts){ pendingShift ->
                ShiftCard(
                    shift = pendingShift,
                    onClick = {
                        navigateToDetailShift(pendingShift.id)
                    },
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PendingShiftsScreenPreview(){
    PendingShiftsScreen(
        navigateBack = {},
        navigateToDetailShift = {}
    )
}