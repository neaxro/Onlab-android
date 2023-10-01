package hu.bme.aut.android.securityapp.ui.feature.common

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDateTime
import java.util.Calendar

@Composable
fun MyDatePickerDialog(
    date: LocalDateTime,
    onConfirm: (Int, Int, Int) -> Unit
){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            onConfirm(selectedYear, selectedMonth+1, selectedDayOfMonth)
        },
        date.year,
        date.month.value - 1,
        date.dayOfMonth
    )

    datePicker.datePicker.maxDate = calendar.timeInMillis

    datePicker.show()
}

@Preview
@Composable
fun MyDatePickerDialogPreview(){
    var selectedDateText by remember { mutableStateOf("No selected date") }
    var show by remember { mutableStateOf(false) }

    TextButton(onClick = { show = true }) {
        Text(text = selectedDateText)
    }

    if(show) {
        MyDatePickerDialog(
            date = LocalDateTime.parse("2023-09-19T21:56:42.4037797"),
            onConfirm = { year, month, date ->
                selectedDateText = "$year.$month.$date."
                show = false
            }
        )
    }
}