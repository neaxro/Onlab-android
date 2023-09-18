package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.data.model.shift.Shift
import hu.bme.aut.android.securityapp.data.model.shift.getEarnedMoney
import hu.bme.aut.android.securityapp.data.model.shift.getElapsedTimeString
import hu.bme.aut.android.securityapp.data.model.shift.getStartTimeString
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.ui.theme.LimeMain

@Composable
fun ManageShiftCard(
    shiftInformation: Shift?,
    wages: List<Wage>,
    wage: Wage,
    onClick: (Boolean) -> Unit,
    onWageChange: (Wage) -> Unit,
    isReadOnly: Boolean,
    modifier: Modifier = Modifier,
){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp, pressedElevation = 2.dp, hoveredElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = LimeMain),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(15.dp)
        ) {

            DataRow(
                title = "Started at:",
                value = shiftInformation?.getStartTimeString() ?: "-"
            )

            DataRow(
                title = "Elapsed time:",
                value = shiftInformation?.getElapsedTimeString() ?: "-"
            )

            DataRow(
                title = "Earned money:",
                value = String.format("%.0f Ft", shiftInformation?.getEarnedMoney() ?: 0.0)
            )

            DataRow(
                title = "Wage",
                value = String.format("%.0f Ft/hour", shiftInformation?.wage?.price ?: wage.price)
            )

            Spacer(modifier = Modifier.padding(top = 20.dp))

            MyDropDownMenu(
                list = wages.map { it.name },
                onItemChange = { wageName ->
                    val selectedWage = wages.first { it.name == wageName}
                    onWageChange(selectedWage)
                },
                icon = Icons.Outlined.Wallet,
                label = "Wage",
                isReadOnly = isReadOnly,
                item = shiftInformation?.wage?.name ?: wage.name
            )

            Spacer(modifier = Modifier.padding(top = 20.dp))

            MyToggleButton(
                textA = "Start Shift",
                textB = "Stop Shift",
                activeA = !isReadOnly,
                onPress = { state ->
                    onClick(state)
                }
            )
        }
    }
}

@Composable
private fun DataRow(title: String, value: String){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
        )
        Text(text = value)
    }
}

@Preview(showBackground = true)
@Composable
fun ManageShiftCardPreview(){
    val wages = mutableListOf<Wage>()
    wages.add(Wage(name = "Kutyas", price = 1000.0))
    wages.add(Wage(name = "Sima", price = 1500.0))
    wages.add(Wage(name = "Specialis", price = 2000.0))

    val shift = Shift(
        startTime = "2023-09-17T22:32:52.220502",
        wage = Wage(price = 1900.0)
    )

    var wage by remember { mutableStateOf<Wage>(wages[0]) }
    var isActive by remember { mutableStateOf<Boolean>(true) }
    var currentShift by remember { mutableStateOf<Shift?>(null) }

    Column {
        ManageShiftCard(
            shiftInformation = currentShift,
            wage = wage,
            wages = wages,
            onClick = {
                isActive = !isActive
                currentShift = if(isActive) shift
                else null
            },
            onWageChange = {
                wage = it
            },
            isReadOnly = isActive
        )
    }
}