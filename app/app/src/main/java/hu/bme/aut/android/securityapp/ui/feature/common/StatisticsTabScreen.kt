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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.securityapp.R

@Composable
fun StatisticsTabScreen(
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.composable_all),
        stringResource(R.string.composable_accepted),
        stringResource(R.string.composable_denied),
        stringResource(R.string.composable_pending)
    )

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
                        0 -> Image(imageVector = Icons.Default.AllInclusive, contentDescription = stringResource(R.string.composable_all))
                        1 -> Image(imageVector = Icons.Default.Check, contentDescription = stringResource(R.string.composable_accepted))
                        2 -> Image(imageVector = Icons.Default.Close, contentDescription = stringResource(R.string.composable_denied))
                        3 -> Image(imageVector = Icons.Default.QuestionMark, contentDescription = stringResource(R.string.composable_pending))
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