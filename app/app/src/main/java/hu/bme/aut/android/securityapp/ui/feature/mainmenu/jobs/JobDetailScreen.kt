package hu.bme.aut.android.securityapp.ui.feature.mainmenu.jobs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Badge
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.people.getProfileBitmap
import hu.bme.aut.android.securityapp.ui.feature.common.MySnackbarHost
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(
    viewModel: JobDetailViewModel = hiltViewModel(),
    navigateEditJob: (Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

    val job = viewModel.job.collectAsState().value

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {

            MyTopAppBar(
                title = job.title,
                onNavigate = onNavigateBack,
                actions = {
                    if (job.owner.id == LoggedPerson.ID) {
                        IconButton(onClick = { navigateEditJob(job.id) }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(
                                R.string.composable_edit
                            ))
                        }
                    }
                },
                screenState = viewModel.screenState.collectAsState()
            )
        },
        snackbarHost = {
            MySnackbarHost(
                screenState = viewModel.screenState,
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .scrollable(scrollState, orientation = Orientation.Vertical)
        ) {

            DataTextBox(
                text = job.description,
                icon = Icons.Rounded.Description,
                description = stringResource(R.string.composable_job_description),
            )

            DataTextBox(
                text = job.pin,
                icon = Icons.Rounded.Key,
                description = stringResource(R.string.composable_connection_pin)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(10.dp)
                    .background(Color(211, 243, 107), RoundedCornerShape(10.dp))
                    .border(3.dp, Color.Black, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {

                if (job.owner.profilePicture != null) {
                    Image(
                        bitmap = job.owner.getProfileBitmap(),
                        contentDescription = stringResource(id = R.string.composable_profile_picture),
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .border(3.dp, Color.Black, RoundedCornerShape(50.dp))
                    )
                } else {
                    Image(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = stringResource(id = R.string.composable_profile_picture),
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.White)
                            .border(3.dp, Color.Black, RoundedCornerShape(50.dp))
                    )
                }

                DataTextBox(
                    text = job.owner.fullName,
                    icon = Icons.Rounded.Person,
                    description = stringResource(R.string.composable_owner_full_name),
                    backgroundColor = Color.White
                )

                DataTextBox(
                    text = job.owner.nickname,
                    icon = Icons.Rounded.Badge,
                    description = stringResource(R.string.composable_owner_nickname),
                    backgroundColor = Color.White
                )

                DataTextBox(
                    text = job.owner.email,
                    icon = Icons.Rounded.Email,
                    description = stringResource(R.string.composable_owner_email_address),
                    backgroundColor = Color.White
                )
            }
        }
    }
}

@Composable
fun DataTextBox(
    text: String,
    icon: ImageVector,
    description: String,
    backgroundColor: Color = Color(211, 243, 107),
    modifier: Modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .border(3.dp, Color.Black, RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Icon(imageVector = icon, contentDescription = description)

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = description,

                )
        }

        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DetailTextPreview() {
    DataTextBox(text = "Nemes Axel Roland", icon = Icons.Default.Person, "Owner")
}