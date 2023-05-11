package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.ui.theme.LimeMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobCard(
    job: DetailedJob,
    modifier: Modifier = Modifier,
    onDetaileClicked: (DetailedJob) -> Unit,
    onClicked: (Int) -> Unit,
    canEdit: Boolean = false
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = LimeMain
        ),
        onClick = {
            onClicked(job.id)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = job.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Column {
                    IconButton(
                        onClick = {
                            onDetaileClicked(job)
                        },
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Info,
                            contentDescription = "Show details",
                        )
                    }
                    /*
                    if (job.owner.profilePicture != null) {
                        Image(
                            bitmap = job.owner.getProfileBitmap(),
                            contentDescription = "Owner profile picture",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape),
                        )
                    }
                    else{
                        Image(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = "Owner profile picture",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape),
                        )
                    }*/
                }

            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.Black)
                    .padding(14.dp)
            ) {
                if (job.description.isNotEmpty()) {
                    Text(
                        text = job.description,
                        color = Color(211, 243, 107),
                        fontSize = 10.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewJobCard() {
    val jobData = DetailedJob(
        2, "Fishong On orfű 2023 nagyon faasdasdasdasd asd asd", "Fishing On Orfű 2023 nagyon faxa feszt Fishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa feszt", "ASD123",
        Person(1, "Nemes Axel Roland", "nemesa", "Axi", "neaxro@gmail.com", null)
    )

    JobCard(
        jobData,
        Modifier,
        {},
        {}
    )
}