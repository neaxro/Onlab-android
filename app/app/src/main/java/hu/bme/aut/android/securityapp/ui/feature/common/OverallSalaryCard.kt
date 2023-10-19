package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.securityapp.ui.theme.LimeMain
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun OverallSalaryCard(
    overallSalary: Double,
    modifier: Modifier = Modifier
){
    val dec = DecimalFormat("###,###,###,###,###", DecimalFormatSymbols(Locale.ENGLISH))

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp, pressedElevation = 2.dp, hoveredElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = LimeMain),
        modifier = modifier
    ) {
        DoubleDataRow(
            title = {
                Text(
                    text = "Total Salary:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            value = {
                BackgroundedText(
                    text = String.format("%s Ft", dec.format(overallSalary).replace(",", " ")),
                    color = Color.White,
                    backgroundColor = Color.Black,
                    cornerShape = RoundedCornerShape(5.dp)
                )
            },
            modifier = Modifier
                .padding(10.dp)
        )
    }
}

@Preview
@Composable
fun OverallSalaryCardPreview(){
    OverallSalaryCard(
        overallSalary = 1278363.12,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
}