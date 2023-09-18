package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.ui.theme.LimeMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToggleButton(
    textA: String,
    textB: String,
    onPress: (Boolean) -> Unit,
    activeA: Boolean,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp, pressedElevation = 2.dp, hoveredElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        onClick = {
            onPress(activeA)
        },
        enabled = enabled,
        modifier = modifier
    ) {
        if(activeA){
            Text(
                text = textA,
                color = LimeMain,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
        }
        else{
            Text(
                text = textB,
                color = LimeMain,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyToggleButtonPreview(){
    var enabled by remember { mutableStateOf<Boolean>(true) }

    Row {
        MyToggleButton(
            textA = "Enabled",
            textB = "Disabled",
            onPress = {
                enabled = !enabled
            },
            activeA = enabled
        )

        MyToggleButton(
            textA = "Start Shift",
            textB = "Stop Shift",
            onPress = {},
            enabled = enabled,
            activeA = enabled
        )
    }
}