package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
        ){
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)

            ) {
                FilterChip(selected = true, onClick = { /*TODO*/ }, label = { Text(text = "Acceped") })
                FilterChip(selected = false, onClick = { /*TODO*/ }, label = { Text(text = "Denied") })
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