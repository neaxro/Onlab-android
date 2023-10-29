package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.data.model.shift.PersonSalaryStatistic
import hu.bme.aut.android.securityapp.ui.theme.LimeMain
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun PersonJobStatisticsCard(
    statistic: PersonSalaryStatistic,
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    val dec = DecimalFormat("###,###,###,###,###", DecimalFormatSymbols(Locale.ENGLISH))

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp, pressedElevation = 2.dp, hoveredElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = LimeMain),
        modifier = modifier
            .height(100.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            MyProfilePictureImage(
                profilePicture = statistic.profilePicture,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp)
            ) {
                Text(
                    text = statistic.personName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                DoubleDataRow(
                    title = {
                        Text(
                            text = stringResource(id = R.string.composable_total_salary),
                            fontWeight = FontWeight.Medium
                        )
                    },
                    value = {
                        BackgroundedText(
                            text = String.format(stringResource(id = R.string.composable_s_ft), dec.format(statistic.fullSalary).replace(",", " ")),
                            color = Color.White,
                            backgroundColor = Color.Black,
                            cornerShape = RoundedCornerShape(5.dp)
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PersonJobStatisticsCardPreview(){
    val stat = PersonSalaryStatistic(
        personName = "Nemes Axel Roland",
        fullSalary = 211023710.013,
    )

    PersonJobStatisticsCard(statistic = stat)
}