package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreMenuItem(
    title: String,
    icon: ImageVector,
    color: Color = Color.Black,
    backgroundColor: Color = Color.LightGray,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp, hoveredElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = color,
            )

            Icon(
                imageVector = icon,
                contentDescription = stringResource(R.string.composable_icon),
                tint = color,
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = false)
fun MoreMenuItemPreview(){
    MoreMenuItem(
        title = "Statistics",
        icon = Icons.Default.QueryStats,
        color = Color.Black,
        backgroundColor = Color(0xAA90CAF9),
        onClick = {}
    )
}