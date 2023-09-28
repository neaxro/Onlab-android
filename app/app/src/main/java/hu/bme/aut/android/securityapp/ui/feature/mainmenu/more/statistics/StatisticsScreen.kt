package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.statistics

import androidx.compose.foundation.layout.Box
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
fun StatisticsScreen(
    navigateBack: () -> Unit,
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
        ){
            Text(
                text = "Statistics",
                modifier = Modifier.align(Alignment.Center)
            )
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