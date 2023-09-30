package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StatisticsTabScreen(
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Accepted", "Denied", "Pending")

    TabRow(
        selectedTabIndex = tabIndex,
        modifier = modifier.fillMaxWidth()
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = tabIndex == index,
                onClick = {
                    tabIndex = index
                    onChange(index)
                },
                icon = {
                    when(index){
                        0 -> Image(imageVector = Icons.Default.AllInclusive, contentDescription = "All")
                        1 -> Image(imageVector = Icons.Default.Check, contentDescription = "Accepted")
                        2 -> Image(imageVector = Icons.Default.Close, contentDescription = "Denied")
                        3 -> Image(imageVector = Icons.Default.QuestionMark, contentDescription = "Pending")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun StatisticsTabScreenPreview(){
    StatisticsTabScreen(
        onChange = {

        }
    )
}