package hu.bme.aut.android.securityapp.ui.feature.wages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.WageListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WagesScreen(
    navigateBack: () -> Unit = {},
    navigateToDetails: (Int) -> Unit = {},
    navigateToCreate: () -> Unit,
    viewModel: WagesViewModel = hiltViewModel()
){
    val listState = rememberLazyListState()

    val state = viewModel.screenState.collectAsState().value
    val wages = viewModel.wages.collectAsState().value

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Wage Categories",
                onNavigate = {
                    navigateBack()
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.loadAllWages()
                    }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToCreate() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Create wage")
            }
        }
    ) {
        val paddingTop = it.calculateTopPadding()



        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
        ){
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                items(wages){ item ->
                    WageListItem(
                        wage = item,
                        onEdit = {
                            navigateToDetails(item.id)
                        },
                        modifier = Modifier
                            .padding(
                                start = 5.dp,
                                end = 5.dp,
                                bottom = 5.dp,
                                top = if(wages.indexOf(item) == 0) 5.dp else 0.dp
                            )
                    )
                }
            }

            if(state is WagesScreenStates.Error){
                Text(
                    text = state.message,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(Color.Gray, RoundedCornerShape(10.dp))
                        .padding(10.dp)

                )
            }
        }
    }
}
