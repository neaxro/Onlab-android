package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.data.model.shift.Shift
import hu.bme.aut.android.securityapp.data.model.shift.Status
import hu.bme.aut.android.securityapp.data.model.shift.getEndDate
import hu.bme.aut.android.securityapp.data.model.shift.getEndTime
import hu.bme.aut.android.securityapp.data.model.shift.getStartDate
import hu.bme.aut.android.securityapp.data.model.shift.getStartTime
import hu.bme.aut.android.securityapp.data.model.wage.Wage

@Composable
fun WorkedDateTimeStartEnd(
    shift: Shift,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = shift.getStartDate())
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(text = shift.getStartTime())
        }

        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = stringResource(id = R.string.composable_fromto))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = shift.getEndDate())
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(text = shift.getEndTime())
        }
    }
}

@Preview
@Composable
fun WorkedDateTimeStartEndPreview(){
    val shift = Shift(
        startTime = "2023-09-19T22:21:46.412107",
        endTime =   "2023-09-20T22:19:21.412107",
        earnedMoney = 12980.4,
        person = Person(fullName = "Nemes Axel Roland", nickname = "Axel"),
        job = DetailedJob(title = "Fishing On Orfű 2023"),
        status = Status(title = "Elbírálásra vár", id = 3),
        wage = Wage(name = "Kutyás", price = 3450.0)
    )

    WorkedDateTimeStartEnd(shift = shift)
}