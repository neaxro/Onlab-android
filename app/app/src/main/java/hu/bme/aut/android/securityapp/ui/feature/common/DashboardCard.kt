package hu.bme.aut.android.securityapp.ui.feature.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.securityapp.data.model.dashboard.Dashboard
import hu.bme.aut.android.securityapp.data.model.dashboard.getCreationDate
import hu.bme.aut.android.securityapp.data.model.dashboard.getCreationTime
import hu.bme.aut.android.securityapp.ui.theme.LimeMain
import java.util.Base64

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardCard(
    dashboard: Dashboard,
    onDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var extended by remember { mutableStateOf(false) }

    Card(
        onClick = {
            extended = !extended
        },
        elevation = CardDefaults.cardElevation(
            //defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = LimeMain
        ),
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (dashboard.creatorProfilePicture != null) {
                    val imageBytes = Base64.getDecoder().decode(dashboard.creatorProfilePicture)
                    val image: Bitmap =
                        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                    Image(
                        bitmap = image.asImageBitmap(),
                        contentDescription = "Person",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(30.dp))
                    )
                } else {
                    Image(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Person",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(30.dp))
                    )
                }

                IconButton(onClick = { onDetails(dashboard.id) }) {
                    Image(imageVector = Icons.Default.MoreHoriz, contentDescription = "Details")
                }
            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = dashboard.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )

                Text(
                    text = dashboard.getCreationDate(),
                    fontWeight = FontWeight.Light
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = dashboard.creatorName,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = dashboard.getCreationTime(),
                    fontWeight = FontWeight.Light
                )
            }
        }

        // Message Body
        if (extended && dashboard.message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = dashboard.message,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun DashboardTag(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 10.sp,
        modifier = Modifier
            .background(Color.Black, RoundedCornerShape(20.dp))
            .padding(vertical = 5.dp, horizontal = 10.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewScreenDasboard() {
    val das = Dashboard(
        1,
        "Üdvözlő üzenet",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus at massa vel neque porta finibus eget ac mi. Mauris eleifend ipsum id ultrices feugiat. Aenean tempus nec eros at tempus. Morbi feugiat vehicula orci quis iaculis. Vestibulum purus nulla, fringilla porta mauris a, semper blandit dolor. Etiam varius porta sem, vel scelerisque nisi sodales quis. Quisque laoreet diam eget tortor malesuada tempus. Nullam ut urna eleifend, egestas ligula sit amet, mollis arcu. Cras sodales eros a justo sollicitudin auctor. In lectus felis, dapibus sit amet cursus et, pulvinar maximus nunc.",
        "Mar 27 22:13",
        "Nemes Axel Roland",
        null,
        1,
        "Sima"
    )
    DashboardCard(das, {})
}