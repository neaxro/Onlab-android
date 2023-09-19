package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.ui.theme.LimeMain

@Composable
fun EnableGpsCard(
    checked: Boolean,
    checkedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp, pressedElevation = 2.dp, hoveredElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = LimeMain),
        modifier = modifier
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
        ) {
            Text(
                "Enable GPS Position",
                fontWeight = FontWeight.Bold,
            )

            SwitchWithIcon(
                icon = Icons.Filled.Check,
                checked = checked,
                checkedChanged = {
                    checkedChanged(it)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EnableGpsCardPreview(

){
    EnableGpsCard(
        checked = true,
        checkedChanged = {}
    )
}