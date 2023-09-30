package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.SimpleShift
import hu.bme.aut.android.securityapp.ui.feature.common.StatisticsTabScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun StatisticsScreen(
    navigateBack: () -> Unit,
    viewModel: StatisticsScreenViewModel = hiltViewModel()
){
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Statistics",
                onNavigate = {
                    navigateBack()
                }
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()
        val lazyState = rememberLazyListState()

        val shifts = viewModel.shifts.collectAsState().value

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
        ){
            StatisticsTabScreen(onChange = { tabIndex ->
                when(tabIndex){
                    1 -> viewModel.changeFilter(StatisticsFilter.Accepted)
                    2 -> viewModel.changeFilter(StatisticsFilter.Denied)
                    3 -> viewModel.changeFilter(StatisticsFilter.Pending)
                    else -> viewModel.changeFilter(StatisticsFilter.All)
                }
            })

            LazyColumn(
                state = lazyState
            ){
                items(items = shifts){ shift ->
                    SimpleShift(
                        shift = shift,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 2.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticsScreenPreview(){
    StatisticsScreen(
        navigateBack = {}
    )
}