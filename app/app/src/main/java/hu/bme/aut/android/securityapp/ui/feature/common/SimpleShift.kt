package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.data.model.shift.Shift
import hu.bme.aut.android.securityapp.data.model.shift.Status
import hu.bme.aut.android.securityapp.data.model.shift.getTotalHours
import hu.bme.aut.android.securityapp.data.model.wage.Wage

@Composable
fun SimpleShift(
    shift: Shift,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
    ) {

        Divider(
            color = when(shift.status.id){
                    3 -> Color.Green
                    4 -> Color.Red
                    else -> Color.Yellow
            },
            modifier = Modifier
                .fillMaxHeight()
                .width(10.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            WorkedDateTimeStartEnd(
                shift = shift,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            DoubleDataRow(
                title = { Text(text = "Total hours:") },
                value = { Text(text = String.format("%.1f hours", shift.getTotalHours())) }
            )

            DoubleDataRow(
                title = { Text(text = "Earned money:", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                value = { Text(text = String.format("%.1f Ft", shift.earnedMoney ?: 0.0), fontSize = 20.sp, fontWeight = FontWeight.Bold) }
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun SimpleShiftPreview(){
    val shift = Shift(
        startTime = "2023-09-19T22:21:46.412107",
        endTime =   "2023-09-20T22:19:21.412107",
        earnedMoney = 12980.4,
        person = Person(fullName = "Nemes Axel Roland", nickname = "Axel"),
        job = DetailedJob(title = "Fishing On Orfű 2023"),
        status = Status(title = "Elbírálásra vár", id = 3),
        wage = Wage(name = "Kutyás", price = 3450.0)
    )

    SimpleShift(
        shift = shift,
        modifier = Modifier.fillMaxWidth()
    )
}