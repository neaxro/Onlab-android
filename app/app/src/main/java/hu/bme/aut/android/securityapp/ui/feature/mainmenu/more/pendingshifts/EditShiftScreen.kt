package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.pendingshifts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.data.model.shift.getEndDate
import hu.bme.aut.android.securityapp.data.model.shift.getEndTime
import hu.bme.aut.android.securityapp.data.model.shift.getStartDate
import hu.bme.aut.android.securityapp.data.model.shift.getStartTime
import hu.bme.aut.android.securityapp.ui.feature.common.DoubleDataRow
import hu.bme.aut.android.securityapp.ui.feature.common.MyDatePicker
import hu.bme.aut.android.securityapp.ui.feature.common.MyDropDownMenu
import hu.bme.aut.android.securityapp.ui.feature.common.MyTimePicker
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditShiftScreen(
    navigateBack: () -> Unit,
    viewModel: EditShiftViewModel = hiltViewModel()
){
    var whatToShow by remember { mutableStateOf<ShowDialog>(ShowDialog.Nothing) }

    val shift = viewModel.shift.collectAsState().value
    val wages = viewModel.wages.collectAsState().value
    val person = viewModel.person.collectAsState().value

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Edit Shift",
                onNavigate = { navigateBack() },
                screenState = viewModel.screenState.collectAsState()
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
                .padding(10.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                DoubleDataRow(
                    title = { Text(text = "Start date:") },
                    value = {
                        TextButton(onClick = {
                            whatToShow = ShowDialog.ShowStartDate
                        }) {
                            Text(text = shift.getStartDate())
                        }
                    }
                )

                DoubleDataRow(
                    title = { Text(text = "Start time:") },
                    value = {
                        TextButton(onClick = {
                            whatToShow = ShowDialog.ShowStartTime
                        }) {
                            Text(text = shift.getStartTime())
                        }
                    }
                )

                DoubleDataRow(
                    title = { Text(text = "End date:") },
                    value = {
                        TextButton(onClick = {
                            whatToShow = ShowDialog.ShowEndDate
                        }) {
                            Text(text = shift.getEndDate())
                        }
                    }
                )

                DoubleDataRow(
                    title = { Text(text = "End time:") },
                    value = {
                        TextButton(onClick = {
                            whatToShow = ShowDialog.ShowEndTime
                        }) {
                            Text(text = shift.getEndTime())
                        }
                    }
                )

                DoubleDataRow(
                    title = { Text(text = "Default Wage Name:") },
                    value = {
                        Text(text = person.wage.name)
                    }
                )

                DoubleDataRow(
                    title = { Text(text = "Default Wage Price:") },
                    value = {
                        Text(text = String.format("%.0f Ft/hour", person.wage.price))
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                DoubleDataRow(
                    title = { Text(text = "Wage:") },
                    value = {
                        MyDropDownMenu(
                            list = wages.map { wage -> wage.name },
                            item = shift.wage.name,
                            onItemChange = { newWageName ->
                                viewModel.evoke(EditShiftAction.SetWage(wageName = newWageName))
                            },
                            icon = Icons.Default.Wallet,
                            label = "Choose wage"
                        )
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                OutlinedButton(
                    onClick = { viewModel.evoke(EditShiftAction.SaveChanges) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = "Save Changes")
                        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                        Text(text = "Edit")
                    }
                }
            }

            when(whatToShow){
                ShowDialog.ShowStartTime -> {
                    AlertDialog(
                        onDismissRequest = { whatToShow = ShowDialog.Nothing },
                    ) {
                        MyTimePicker(
                            date = parseStringToLocalDateTime(shift.startTime),
                            onConfirm = { newTime ->
                                whatToShow = ShowDialog.Nothing
                                viewModel.evoke(EditShiftAction.SetStartTime(newTime))
                            },
                        )
                    }
                }
                ShowDialog.ShowStartDate -> {
                    AlertDialog(
                        onDismissRequest = { whatToShow = ShowDialog.Nothing },
                    ) {
                        MyDatePicker(
                            date = parseStringToLocalDateTime(shift.startTime),
                            onConfirm = { newDate ->
                                whatToShow = ShowDialog.Nothing
                                viewModel.evoke(EditShiftAction.SetStartDate(time = newDate))
                            },
                            title = {
                                Text(text = whatToShow.title)
                            }
                        )
                    }
                }
                ShowDialog.ShowEndTime -> {
                    AlertDialog(
                        onDismissRequest = { whatToShow = ShowDialog.Nothing },
                    ) {
                        MyTimePicker(
                            date = parseStringToLocalDateTime(shift.endTime!!),
                            onConfirm = { newTime ->
                                whatToShow = ShowDialog.Nothing
                                viewModel.evoke(EditShiftAction.SetEndTime(newTime))
                            },
                        )
                    }
                }
                ShowDialog.ShowEndDate -> {
                    AlertDialog(
                        onDismissRequest = { whatToShow = ShowDialog.Nothing },
                    ) {
                        MyDatePicker(
                            date = parseStringToLocalDateTime(shift.endTime!!),
                            onConfirm = { newDate ->
                                whatToShow = ShowDialog.Nothing
                                viewModel.evoke(EditShiftAction.SetEndDate(time = newDate))
                            },
                            title = {
                                Text(text = whatToShow.title)
                            }
                        )
                    }
                }
                ShowDialog.Nothing -> { /* Show Nothing */ }
            }
        }
    }
}

fun parseStringToLocalDateTime(dateTimeString: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    return LocalDateTime.parse(dateTimeString, formatter)
}

sealed class ShowDialog(val title: String = ""){
    object ShowStartTime : ShowDialog(title = "Select Start Time")
    object ShowStartDate : ShowDialog(title = "Select Start Date")
    object ShowEndTime : ShowDialog(title = "Select End Time")
    object ShowEndDate : ShowDialog(title = "Select End Date")
    object Nothing : ShowDialog()
}

@Preview(showBackground = true)
@Composable
fun EditShiftScreenPreview(){
    EditShiftScreen(
        navigateBack = {}
    )
}