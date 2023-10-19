package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.jobstatistics

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar

@Composable
fun JobStatisticsScreen(
    navigateBack: () -> Unit,
){

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Job Statistics",
                onNavigate = { navigateBack() }
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()
    }
}