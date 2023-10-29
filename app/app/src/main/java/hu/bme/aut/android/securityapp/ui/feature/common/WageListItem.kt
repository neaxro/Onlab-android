package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import hu.bme.aut.android.securityapp.ui.theme.LimeMain
import java.text.DecimalFormat

@Composable
fun WageListItem(
    wage: Wage,
    onEdit: (Wage) -> Unit = {},
    modifier: Modifier = Modifier,
){
    val decimalFormatter = DecimalFormat("###,###.##")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .background(color = LimeMain, shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth(0.8f)
        ) {
            Text(
                text = wage.name,
                fontWeight = FontWeight.Bold
            )
            Text(text = stringResource(
                R.string.composable_value_ft_hour,
                decimalFormatter.format(wage.price)
            ))
        }

        IconButton(
            onClick = { onEdit(wage) }
        ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(R.string.composable_edit_category))
        }
    }
}

@Composable
@Preview(showBackground = false)
fun WageListItemPreview(){
    val w = Wage(1, "Default", 1300.90, "East Fest")
    Column {
        WageListItem(wage = w)
        WageListItem(wage = w.copy(price = 123.0))
        WageListItem(wage = w.copy(name = "valami hosszabb nev"))
    }
}