package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BackgroundedText(
    text: String,
    color: Color,
    fontWeight: FontWeight = FontWeight.Medium,
    backgroundColor: Color,
    cornerShape: RoundedCornerShape,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(cornerShape)
            .background(color = backgroundColor)
            .padding(5.dp)
    ) {
        Text(
            text = text,
            color = color,
            fontWeight = fontWeight
        )
    }
}