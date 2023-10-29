package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
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
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePicker(
    date: LocalDateTime,
    onConfirm: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier
){
    val timePickerState = remember{
        TimePickerState(
            initialHour = date.hour,
            initialMinute = date.minute,
            is24Hour = true
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(Color.White)
    ) {
        TimePicker(
            state = timePickerState,
        )

        OutlinedButton(onClick = {
            val selectedTime = date.copy(hour = timePickerState.hour, minute = timePickerState.minute)
            onConfirm(selectedTime)
        }) {
            Text(text = stringResource(id = R.string.composable_confirm))
        }
    }
}

fun LocalDateTime.copy(
    year: Int = this.year,
    month: Int = this.monthValue,
    day: Int = this.dayOfMonth,
    hour: Int = this.hour,
    minute: Int = this.minute,
    second: Int = this.second,
    nano: Int = this.nano,
): LocalDateTime{
    return LocalDateTime.of(
        year,
        month,
        day,
        hour,
        minute,
        second,
        nano
    )
}

@Preview
@Composable
fun MyTimePickerPreview(){
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = text)

        MyTimePicker(
            date = LocalDateTime.now(),
            onConfirm = {
                text = it.toString()
            }
        )
    }
}