package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.securityapp.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    date: LocalDateTime,
    onConfirm: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)
){
    val datePickerDialogState = remember {
        DatePickerState(
            initialSelectedDateMillis = date.toMillis(),
            yearRange = (date.year..date.year),
            initialDisplayMode = DisplayMode.Picker,
            initialDisplayedMonthMillis = null
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(Color.White)
    ) {
        DatePicker(
            state = datePickerDialogState,
            title = title,
            dateValidator = { timestamp ->
                timestamp <= Instant.now().toEpochMilli()
            },
            showModeToggle = false,
        )

        OutlinedButton(onClick = {
            val selectedDate = convertLongToDateTime(datePickerDialogState.selectedDateMillis!!)
                .copy(hour = date.hour, minute = date.minute, second = date.second, nano = date.nano)
            onConfirm(selectedDate)
        }) {
            Text(text = stringResource(R.string.composable_confirm))
        }
    }
}

fun LocalDateTime.toMillis(): Long{
    return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun convertLongToDateTime(timestamp: Long): LocalDateTime {
    val instant = Instant.ofEpochMilli(timestamp)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
}

@Preview
@Composable
fun MyDatePickerDialogPreview(){
    var selectedDateText by remember { mutableStateOf("No selected date") }
    var show by remember { mutableStateOf(true) }

    Column {
        TextButton(onClick = { show = !show }) {
            Text(text = selectedDateText)
        }

        if(show) {
            MyDatePicker(
                date = LocalDateTime.parse("2023-09-19T21:56:42.4037797"),
                onConfirm = {
                    selectedDateText = it.toString()
                    show = false
                },
                title = {
                    Text(text = "Select Start date")
                }
            )
        }
    }
}