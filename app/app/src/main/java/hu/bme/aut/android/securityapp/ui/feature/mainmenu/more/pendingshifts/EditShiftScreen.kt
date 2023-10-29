package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.pendingshifts

import android.content.Context
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.data.model.shift.getEndDate
import hu.bme.aut.android.securityapp.data.model.shift.getEndTime
import hu.bme.aut.android.securityapp.data.model.shift.getStartDate
import hu.bme.aut.android.securityapp.data.model.shift.getStartTime
import hu.bme.aut.android.securityapp.ui.feature.common.DoubleDataRow
import hu.bme.aut.android.securityapp.ui.feature.common.MyDatePicker
import hu.bme.aut.android.securityapp.ui.feature.common.MyDropDownMenu
import hu.bme.aut.android.securityapp.ui.feature.common.MySnackbarHost
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
    val context = LocalContext.current
    var whatToShow by rememberSaveable { mutableStateOf<ShowDialog>(ShowDialog.Nothing()) }

    val shift = viewModel.shift.collectAsState().value
    val wages = viewModel.wages.collectAsState().value
    val person = viewModel.person.collectAsState().value

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.composable_edit_shift_title),
                onNavigate = { navigateBack() },
                screenState = viewModel.screenState.collectAsState()
            )
        },
        snackbarHost = {
            MySnackbarHost(
                screenState = viewModel.screenState,
            )
        },
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
                    title = { Text(text = stringResource(R.string.composable_start_date)) },
                    value = {
                        TextButton(onClick = {
                            whatToShow = ShowDialog.ShowStartDate(context = context)
                        }) {
                            Text(text = shift.getStartDate())
                        }
                    }
                )

                DoubleDataRow(
                    title = { Text(text = stringResource(R.string.composable_start_time)) },
                    value = {
                        TextButton(onClick = {
                            whatToShow = ShowDialog.ShowStartTime(context = context)
                        }) {
                            Text(text = shift.getStartTime())
                        }
                    }
                )

                DoubleDataRow(
                    title = { Text(text = stringResource(R.string.composable_end_date)) },
                    value = {
                        TextButton(onClick = {
                            whatToShow = ShowDialog.ShowEndDate(context = context)
                        }) {
                            Text(text = shift.getEndDate())
                        }
                    }
                )

                DoubleDataRow(
                    title = { Text(text = stringResource(R.string.composable_end_time)) },
                    value = {
                        TextButton(onClick = {
                            whatToShow = ShowDialog.ShowEndTime(context = context)
                        }) {
                            Text(text = shift.getEndTime())
                        }
                    }
                )

                DoubleDataRow(
                    title = { Text(text = stringResource(R.string.composable_default_wage_name)) },
                    value = {
                        Text(text = person.wage.name)
                    }
                )

                DoubleDataRow(
                    title = { Text(text = stringResource(R.string.composable_default_wage_price)) },
                    value = {
                        Text(text = String.format(stringResource(id = R.string.composable_0f_ft_hour), person.wage.price))
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
                            label = stringResource(R.string.composable_choose_wage)
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
                        Icon(imageVector = Icons.Default.Save, contentDescription = stringResource(R.string.composable_save_changes))
                        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                        Text(text = "Edit")
                    }
                }
            }

            when(whatToShow){
                is ShowDialog.ShowStartTime -> {
                    AlertDialog(
                        onDismissRequest = { whatToShow = ShowDialog.Nothing() },
                    ) {
                        MyTimePicker(
                            date = parseStringToLocalDateTime(shift.startTime),
                            onConfirm = { newTime ->
                                whatToShow = ShowDialog.Nothing()
                                viewModel.evoke(EditShiftAction.SetStartTime(newTime))
                            },
                        )
                    }
                }
                is ShowDialog.ShowStartDate -> {
                    AlertDialog(
                        onDismissRequest = { whatToShow = ShowDialog.Nothing() },
                    ) {
                        MyDatePicker(
                            date = parseStringToLocalDateTime(shift.startTime),
                            onConfirm = { newDate ->
                                whatToShow = ShowDialog.Nothing()
                                viewModel.evoke(EditShiftAction.SetStartDate(time = newDate))
                            },
                            title = {
                                Text(text = whatToShow.title)
                            }
                        )
                    }
                }
                is ShowDialog.ShowEndTime -> {
                    AlertDialog(
                        onDismissRequest = { whatToShow = ShowDialog.Nothing() },
                    ) {
                        MyTimePicker(
                            date = parseStringToLocalDateTime(shift.endTime!!),
                            onConfirm = { newTime ->
                                whatToShow = ShowDialog.Nothing()
                                viewModel.evoke(EditShiftAction.SetEndTime(newTime))
                            },
                        )
                    }
                }
                is ShowDialog.ShowEndDate -> {
                    AlertDialog(
                        onDismissRequest = { whatToShow = ShowDialog.Nothing() },
                    ) {
                        MyDatePicker(
                            date = parseStringToLocalDateTime(shift.endTime!!),
                            onConfirm = { newDate ->
                                whatToShow = ShowDialog.Nothing()
                                viewModel.evoke(EditShiftAction.SetEndDate(time = newDate))
                            },
                            title = {
                                Text(text = whatToShow.title)
                            }
                        )
                    }
                }
                is ShowDialog.Nothing -> { /* Show Nothing */ }
            }
        }
    }
}

fun parseStringToLocalDateTime(dateTimeString: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    return LocalDateTime.parse(dateTimeString, formatter)
}

sealed class ShowDialog(val title: String = "", val context: Context?){
    class ShowStartTime(context: Context) : ShowDialog(title = context.getString(R.string.composable_select_start_time), context = context)
    class ShowStartDate(context: Context) : ShowDialog(title = context.getString(R.string.composable_select_start_date), context = context)
    class ShowEndTime(context: Context) : ShowDialog(title = context.getString(R.string.composable_select_end_time), context = context)
    class ShowEndDate(context: Context) : ShowDialog(title = context.getString(R.string.composable_select_end_date), context = context)
    class Nothing : ShowDialog(context = null)
}

@Preview(showBackground = true)
@Composable
fun EditShiftScreenPreview(){
    EditShiftScreen(
        navigateBack = {}
    )
}