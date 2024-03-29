package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.data.model.shift.Shift
import hu.bme.aut.android.securityapp.data.model.shift.getEarnedMoney
import hu.bme.aut.android.securityapp.data.model.shift.getElapsedTimeString
import hu.bme.aut.android.securityapp.data.model.shift.getStartDate
import hu.bme.aut.android.securityapp.data.model.shift.getStartTime
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.ui.theme.LimeMain

@Composable
fun ManageShiftCard(
    shiftInformation: Shift?,
    wages: List<Wage>,
    wage: Wage,
    onClick: (Boolean) -> Unit,
    onWageChange: (Wage) -> Unit,
    enabled: Boolean,
    buttonEnabled: Boolean,
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

            DoubleDataRow(
                title = { Text(text = stringResource(R.string.composable_started_at)) },
                value = {
                    Column {
                        Text(text = shiftInformation?.getStartDate() ?: "-")
                        Text(text = shiftInformation?.getStartTime() ?: "-")
                    }
                },
                verticalAlignment = Alignment.Top
            )

            DoubleDataRow(
                title = { Text(text = stringResource(R.string.composable_earned_money)) },
                value = { Text(text = shiftInformation?.getElapsedTimeString() ?: "-") },
            )

            DoubleDataRow(
                title = { Text(text = stringResource(R.string.composable_elapsed_time)) },
                value = { Text(text = String.format(stringResource(R.string.composable_0f_ft), shiftInformation?.getEarnedMoney() ?: 0.0)) },
            )

            DoubleDataRow(
                title = { Text(text = stringResource(R.string.composable_wage)) },
                value = { Text(text = String.format(stringResource(R.string.composable_0f_ft_hour), shiftInformation?.wage?.price ?: wage.price)) },
            )

            Spacer(modifier = Modifier.padding(top = 20.dp))

            MyDropDownMenu(
                list = wages.map { it.name },
                onItemChange = { wageName ->
                    val selectedWage = wages.first { it.name == wageName}
                    onWageChange(selectedWage)
                },
                icon = Icons.Outlined.Wallet,
                label = stringResource(R.string.composable_wage),
                isReadOnly = enabled,
                item = shiftInformation?.wage?.name ?: wage.name
            )

            Spacer(modifier = Modifier.padding(top = 20.dp))

            MyToggleButton(
                textA = stringResource(R.string.composable_start_shift),
                textB = stringResource(R.string.composable_stop_shift),
                activeA = !enabled,
                onPress = { state ->
                    onClick(state)
                },
                enabled = buttonEnabled
            )
        }
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
    var currentShift by remember { mutableStateOf<Shift?>(shift) }

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
            enabled = isActive,
            buttonEnabled = true
        )
    }
}