package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DoubleDataRow(
    title: @Composable RowScope.() -> Unit,
    value: @Composable RowScope.() -> Unit,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        modifier = modifier
            .fillMaxWidth()
    ) {
        title()
        value()
    }
}

@Preview(showBackground = true)
@Composable
fun DoubleDataRowPreview(){
    DoubleDataRow(
        title = { Column {
            Text(text = "Hello")
            Text(text = "Hello")
            Text(text = "Hello")
        } },
        value = { Text(text = "World") },
        verticalAlignment = Alignment.Top,
    )
}