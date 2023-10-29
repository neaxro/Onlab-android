package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.data.model.shift.Shift
import hu.bme.aut.android.securityapp.data.model.shift.Status
import hu.bme.aut.android.securityapp.data.model.shift.getEndDate
import hu.bme.aut.android.securityapp.data.model.shift.getEndTime
import hu.bme.aut.android.securityapp.data.model.shift.getStartDate
import hu.bme.aut.android.securityapp.data.model.shift.getStartTime
import hu.bme.aut.android.securityapp.data.model.shift.getTotalHours
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.ui.theme.LimeMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftCard(
    shift: Shift,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp, pressedElevation = 2.dp, hoveredElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = LimeMain),
        onClick = {
            onClick(shift.id)
        },
        modifier = modifier
    ) {
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
                .background(Color.Black)
                .padding(10.dp)
            ){
                Column {
                    Text(
                        text = shift.person.fullName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = LimeMain
                    )
                    Text(
                        text = String.format(stringResource(R.string.composable_1f_hours), shift.getTotalHours()),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = LimeMain,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = shift.getStartDate())
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                    Text(text = shift.getStartTime())
                }

                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = stringResource(R.string.composable_fromto))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = shift.getEndDate())
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                    Text(text = shift.getEndTime())
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BackgroundedText(
                    text = shift.wage.name,
                    color = LimeMain,
                    backgroundColor = Color.Black,
                    cornerShape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(5.dp)
                )

                BackgroundedText(
                    text = String.format(stringResource(R.string.composable_1f_ft_hour), shift.wage.price),
                    color = LimeMain,
                    backgroundColor = Color.Black,
                    cornerShape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(5.dp)
                )
            }

            BackgroundedText(
                text = String.format(stringResource(R.string.composable_1f_ft), shift.earnedMoney),
                color = LimeMain,
                fontWeight = FontWeight.ExtraBold,
                backgroundColor = Color.Black,
                cornerShape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShiftCardPreview(){
    val shift = Shift(
        startTime = "2023-09-19T22:21:46.412107",
        endTime =   "2023-09-20T22:19:21.412107",
        earnedMoney = 12980.4,
        person = Person(fullName = "Nemes Axel Roland", nickname = "Axel"),
        job = DetailedJob(title = "Fishing On Orfű 2023"),
        status = Status(title = "Elbírálásra vár"),
        wage = Wage(name = "Kutyás", price = 3450.0)
    )

    Column {
        ShiftCard(
            shift = shift,
            onClick = {},
            modifier = Modifier.padding(5.dp)
        )

        ShiftCard(
            shift = shift.copy(
                wage = Wage(name = "Kutyás hosszú", price = 3450.0)
            ),
            onClick = {},
            modifier = Modifier.padding(5.dp)
        )
    }
}