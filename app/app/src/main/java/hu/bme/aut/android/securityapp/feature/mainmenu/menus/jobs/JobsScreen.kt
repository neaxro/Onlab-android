package hu.bme.aut.android.securityapp.feature.mainmenu.menus

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddLink
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.jobs.JobsViewModel
import hu.bme.aut.android.securityapp.ui.theme.LimeMain
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun JobsScreen(
    viewModel: JobsViewModel,
    navigateToCreateJob: () -> Unit,
    navigateToConnectJob: () -> Unit,
    navigateToDetailJob: (Int) -> Unit,
) {
    val context = LocalContext.current
    val scrollState = rememberLazyStaggeredGridState()
    val jobList = remember { viewModel.jobs }
    viewModel.loadAllJobs()

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 10.dp,
                tonalElevation = 5.dp
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Jobs",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                    ),
                    actions = {

                        IconButton(onClick = {
                            viewModel.loadAllJobs()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Refresh,
                                contentDescription = "Refresh"
                            )
                        }

                        IconButton(onClick = {
                            navigateToCreateJob()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "Create new job"
                            )
                        }

                        IconButton(onClick = {
                            navigateToConnectJob()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.AddLink,
                                contentDescription = "Connect for job"
                            )
                        }

                    },
                )
            }

        }
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(1),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    start = 10.dp,
                    end = 10.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalItemSpacing = 10.dp,
            state = scrollState
        ) {
            items(jobList) { item ->
                JobCard(
                    job = item,
                    onDetaileClicked = {
                        navigateToDetailJob(item.id)
                    },
                    onClicked = {
                        // TODO: network call
                        Toast.makeText(context, "Clicked item with ID: $it!", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobCard(
    job: DetailedJob,
    modifier: Modifier = Modifier,
    onDetaileClicked: (DetailedJob) -> Unit,
    onClicked: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        //.height(250.dp),
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
                if (job.owner.profilePicture != null) {
                    val imageBytes = Base64.getDecoder().decode(job.owner.profilePicture)
                    val image: Bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                    Image(
                        bitmap = image.asImageBitmap(),
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
                }

                IconButton(
                    modifier = Modifier,
                    onClick = {
                        onDetaileClicked(job)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = "Show details",
                    )
                }
            }

            Text(
                text = job.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.Black)
                    .padding(10.dp)
            ) {
                if (job.description.isNotEmpty()) {
                    Text(
                        text = job.description,
                        color = Color(211, 243, 107),
                        fontSize = 15.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewScreenJobs() {
    JobsScreen(
        viewModel = viewModel(),
        {},
        {},
        {}
    )
}

@Preview(showBackground = false)
@Composable
fun PreviewJobCard() {
    val jobData = DetailedJob(
        2, "Fishong On orfű 2023 nagyon fa", "Fishing On Orfű 2023 nagyon faxa feszt Fishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa fesztFishing On Orfű 2023 nagyon faxa feszt", "ASD123",
        Person(1, "Nemes Axel Roland", "nemesa", "Axi", "neaxro@gmail.com", null)
    )

    JobCard(
        jobData,
        Modifier,
        {},
        {}
    )
}