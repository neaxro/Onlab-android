package hu.bme.aut.android.securityapp.feature.mainmenu.menus

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddLink
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.ui.feature.common.JobCard
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.jobs.JobsViewModel

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

    viewModel.loadAllJobs(){ errorMessage ->
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {

            MyTopAppBar(
                title = "Jobs",
                actions = {
                    IconButton(onClick = {
                        viewModel.loadAllJobs(){ errorMessage ->
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
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
                }
            )
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
            verticalItemSpacing = 5.dp,
            state = scrollState
        ) {
            items(jobList) { item ->
                JobCard(
                    job = item,
                    onDetaileClicked = {
                        navigateToDetailJob(item.id)
                    },
                    onClicked = {
                        Log.d("JOB_PERSON", "[SCREEN] JobId: ${LoggedPerson.CURRENT_JOB_ID} \tPersonId: ${LoggedPerson.ID}")
                        viewModel.selectJob(
                            item.id,
                            onSuccess = { successMessage ->
                                //Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
                            },
                            onError = { errorMessage ->
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    modifier = Modifier.padding(top = 5.dp)
                )
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