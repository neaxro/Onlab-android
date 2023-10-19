package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.jobstatistics

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.ui.feature.common.MySnackbarHost
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.OverallSalaryCard
import hu.bme.aut.android.securityapp.ui.feature.common.PersonJobStatisticsCard

@Composable
fun JobStatisticsScreen(
    navigateBack: () -> Unit,
    viewModel: JobStatisticsViewModel = hiltViewModel()
){
    val listState = rememberLazyListState()

    val context = LocalContext.current
    val statistics = viewModel.statistic.collectAsState().value

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Job Statistics",
                onNavigate = { navigateBack() },
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
                .padding(5.dp)
        ){
            items(statistics.peopleSalaryStatistics){ statistic ->
                if(statistics.peopleSalaryStatistics.first() == statistic){
                    OverallSalaryCard(
                        overallSalary = statistics.overallSalary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                    )
                }

                PersonJobStatisticsCard(
                    statistic = statistic,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                )
            }
        }
    }
}